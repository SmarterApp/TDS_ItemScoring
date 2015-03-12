package tds.itemscoringengine.itemscorers;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.cassandra.io.util.MemoryOutputStream;
import org.apache.commons.io.output.XmlStreamWriter;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import AIR.Common.Utilities.SpringApplicationContext;
import AIR.Common.Web.HttpWebHelper;
import scala.collection.immutable.StringOps;
import tds.itemscoringengine.IItemScorer;
import tds.itemscoringengine.IItemScorerCallback;
import tds.itemscoringengine.ItemScore;
import tds.itemscoringengine.ItemScoreRequest;
import tds.itemscoringengine.ItemScoreResponse;
import tds.itemscoringengine.ResponseInfo;
import tds.itemscoringengine.RubricContentSource;
import tds.itemscoringengine.ScoreRationale;
import tds.itemscoringengine.ScorerInfo;
import tds.itemscoringengine.ScoringStatus;
import tds.itemscoringengine.WebProxyItemScorerCallback;

public class ProxyItemScorer implements IItemScorer
{
  private static final Logger        _logger     = LoggerFactory.getLogger (ProxyItemScorer.class);
  private HttpWebHelper              _httpClient = null;

  public ProxyItemScorer () {
    _httpClient = SpringApplicationContext.getBean ("httpWebHelper", HttpWebHelper.class);
  }

  // / <summary>
  // / Scorer meta information
  // / </summary>
  // / <param name="itemFormat"></param>
  // / <returns></returns>
  @Override
  public ScorerInfo GetScorerInfo (String itemFormat) {
    // TODO shiva: rubric caching is not currently supported. fix it. the
    // IRubric implementation for QTI - QTIRubricScorer is already there.
    return new ScorerInfo ("1.0", true, false, RubricContentSource.ItemXML);
  }

  @Override
  public ItemScore ScoreItem (ResponseInfo responseInfo, IItemScorerCallback callback) {
    if (!(callback instanceof WebProxyItemScorerCallback))
      return new ItemScore (-1, -1, ScoringStatus.NotScored, "overall", new ScoreRationale (), responseInfo.getContextToken ()); // We

    ItemScoreRequest request = new ItemScoreRequest (responseInfo);

    // Lets get the item scoring server URL from the URI provider given at
    // constructor (if provided)
    WebProxyItemScorerCallback webProxyCallback = (WebProxyItemScorerCallback) callback;
    String serverUrl = webProxyCallback.getServerURL ();

    if (StringUtils.isEmpty (serverUrl))
      throw new RuntimeException ("Proxy item scorer was not provided a server url.");
    request.setCallbackUrl (webProxyCallback.getCallbackURL ());
    // send to server and wait for response
    try {
      return sendRequest (request, serverUrl).getScore ();
    } catch (final Exception exp) {
      return new ItemScore (-1, -1, ScoringStatus.ScoringError, "overall", new ScoreRationale ()
      {
        {
          setMsg (exp.toString ());
        }
      }, responseInfo.getContextToken ()); // We
    }
  }

  @Override
  public void shutdown () {
    // TODO Auto-generated method stub

  }

  private ItemScoreResponse sendRequest (ItemScoreRequest request, String url) throws Exception {
    ByteArrayOutputStream os = new ByteArrayOutputStream ();
    _httpClient.sendXml (url, request, os);

    return ItemScoreResponse.getInstanceFromXml (new String (os.toByteArray ()));
  }
}
