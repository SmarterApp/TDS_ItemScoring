/*******************************************************************************
 * Educational Online Test Delivery System 
 * Copyright (c) 2014 American Institutes for Research
 *   
 * Distributed under the AIR Open Source License, Version 1.0 
 * See accompanying file AIR-License-1_0.txt or at
 * http://www.smarterapp.org/documents/American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
package tds.itemscoringengine;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import AIR.Common.Threading.AbstractTask;
import AIR.Common.Threading.BoundedThreadPool;
import AIR.Common.Threading.ITask;
import AIR.Common.Threading.IThreadPoolExceptionHandler;
import AIR.Common.Threading.IThreadPoolStats;
import AIR.Common.Threading.IThreadPoolStatsRecorder;
import AIR.Common.Threading.ThreadPoolStatsRecorder;
import AIR.Common.Web.IUrlRewriter;

public class ItemScorerManagerImpl implements IItemScorerManager
{

  private static final Logger            _logger            = LoggerFactory.getLogger (ItemScorerManagerImpl.class);

  private final Map<String, IItemScorer> _scoringEngines    = new HashMap<String, IItemScorer> ();
  private final BoundedThreadPool        _threadPool;
  private IUrlRewriter                   _rubricUrlRewriter = null;

  public ItemScorerManagerImpl (int threadCount, int numTasksHighWaterMark, int numTasksLowWaterMark) {
    this (threadCount, numTasksHighWaterMark, numTasksLowWaterMark, new ThreadPoolStatsRecorder ());
  }

  public ItemScorerManagerImpl (int threadCount, int numTasksHighWaterMark, int numTasksLowWaterMark, IThreadPoolStatsRecorder statsRecorder) {
    _threadPool = new BoundedThreadPool (threadCount, "ItemScorerManagerImpl", numTasksHighWaterMark, numTasksLowWaterMark, statsRecorder);
    IThreadPoolExceptionHandler handler = new IThreadPoolExceptionHandler ()
    {

      @Override
      public void onException (ITask task, Throwable t) {
        _logger.error (t.getMessage (), t);
      }
    };
    ((BoundedThreadPool) _threadPool).setExceptionHandler (handler);
  }

  public ItemScorerManagerImpl (Map<String, IItemScorer> engines, int threadCount, int numTasksHighWaterMark, int numTasksLowWaterMark) {
    this (threadCount, numTasksHighWaterMark, numTasksLowWaterMark);

    for (Map.Entry<String, IItemScorer> entry_i : engines.entrySet ()) {
      RegisterItemScorer (entry_i.getKey (), entry_i.getValue ());
    }
  }

  @Override
  public ScorerInfo GetScorerInfo (String itemFormat) {
    IItemScorer itemScorer = _scoringEngines.get (itemFormat);
    if (itemScorer == null) {
      return null;
    }
    return itemScorer.GetScorerInfo (itemFormat);
  }

  @Override
  public ItemScore ScoreItem (ResponseInfo studentResponse, IItemScorerCallback callbackReference) {

    // Apply any rewriting rule to the request
    if (_rubricUrlRewriter != null && studentResponse.getContentType () == RubricContentType.Uri) {
      try {
        studentResponse.setRubric (_rubricUrlRewriter.rewrite (studentResponse.getRubric ()));
      } catch (final MalformedURLException e) {
        _logger.error (e.getMessage (), e);
        return new ItemScore (-1, -1, ScoringStatus.ScoringError, null, new ScoreRationale ()
        {
          {
            setMsg (e.getMessage ());
          }
        }, studentResponse.getContextToken ());
      }
    }

    // Locate the appropriate scorer
    final String itemFormat = studentResponse.getItemFormat ();
    IItemScorer itemScorerImpl = _scoringEngines.get (itemFormat);
    if (itemScorerImpl == null) {
      return new ItemScore (-1, -1, ScoringStatus.NoScoringEngine, null, new ScoreRationale ()
      {
        {
          setMsg ("No scoring engine found for " + itemFormat);
        }
      }, null, studentResponse.getContextToken ());
    }

    // Score the response.
    if (callbackReference == null) {
      return InvokeSynchronousScoring (itemScorerImpl, studentResponse);
    } else {
      return InvokeAsynchronousScoring (itemScorerImpl, studentResponse, callbackReference);
    }
  }

  @Override
  public void RegisterItemScorer (String itemFormat, IItemScorer itemScorer) {
    _scoringEngines.put (itemFormat, itemScorer);
  }

  @Override
  public IUrlRewriter getRubricUrlRewriter () {
    return _rubricUrlRewriter;
  }

  @Override
  public void setRubricUrlRewriter (IUrlRewriter value) {
    _rubricUrlRewriter = value;
  }

  public IThreadPoolStats getThreadPoolStats () {
    return _threadPool.getStats ();
  }

  @Override
  public void shutdown () {
    for (IItemScorer engine_i : _scoringEngines.values ()) {
      engine_i.shutdown ();
    }
    _scoringEngines.clear ();
    _threadPool.shutdown (true);
  }

  private ItemScore InvokeAsynchronousScoring (IItemScorer itemScorerImpl, ResponseInfo studentResponse, IItemScorerCallback callbackReference) {
    // if the scorer supports async mode then let the item scorer server deal
    // with async
    if (itemScorerImpl.GetScorerInfo (studentResponse.getItemFormat ()).isSupportsAsyncMode ()) {
      return itemScorerImpl.ScoreItem (studentResponse, callbackReference);
    }

    // if the scorer does not handle async mode then we need to handle it in our
    // own thread queue
    if (_threadPool.Enqueue (new AsyncScoringTask (itemScorerImpl, studentResponse, callbackReference))) {
      return new ItemScore (-1, -1, ScoringStatus.WaitingForMachineScore, null, null, null, studentResponse.getContextToken ());
    }

    // if we get here then the thread queue is filled (probably waiting on a
    // bunch of scores to come back)
    return new ItemScore (-1, -1, ScoringStatus.WaitingForMachineScore, null, new ScoreRationale ()
    {
      {
        setMsg ("Cannot enqueue scoring task");
      }
    }, null, studentResponse.getContextToken ());
  }

  private ItemScore InvokeSynchronousScoring (IItemScorer itemScorerImpl, ResponseInfo studentResponse) {
    return itemScorerImpl.ScoreItem (studentResponse, null);
  }

  private static class AsyncScoringTask extends AbstractTask
  {
    IItemScorer         _itemScorerImpl;
    ResponseInfo        _studentResponse;
    IItemScorerCallback _callback;

    public AsyncScoringTask (IItemScorer scorer, ResponseInfo studentResponse, IItemScorerCallback callback) {
      _itemScorerImpl = scorer;
      _studentResponse = studentResponse;
      _callback = callback;
    }

    @Override
    public void Execute () {
      ItemScore score = null;

      // Step 1: Score the Item
      try {
        // Note: This is sync but it is OK since it is executed by 1 of the
        // thread pool threads so it is async to the original caller
        score = _itemScorerImpl.ScoreItem (_studentResponse, null);
      } catch (final Exception ex) {
        // Exception scoring item. Prepare an error report
        score = new ItemScore (-1, -1, ScoringStatus.ScoringError, null, new ScoreRationale ()
        {
          {
            setMsg ("Exception scoring item " + _studentResponse.getItemIdentifier () + "\n" + ex);
          }
        }, null, _studentResponse.getContextToken ());
      }

      // Step 2: Use the callback to report the score
      try {
        _callback.ScoreAvailable (score, _studentResponse);
      } catch (Exception ex) {
        _logger.error ("Exception Reporting Score", ex);
      }
    }
  }
}
