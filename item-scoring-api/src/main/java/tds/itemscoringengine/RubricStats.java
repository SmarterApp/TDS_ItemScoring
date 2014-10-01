/*******************************************************************************
 * Educational Online Test Delivery System 
 * Copyright (c) 2014 American Institutes for Research
 *   
 * Distributed under the AIR Open Source License, Version 1.0 
 * See accompanying file AIR-License-1_0.txt or at
 * http://www.smarterapp.org/documents/American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
package tds.itemscoringengine;

import java.util.concurrent.TimeUnit;

import AIR.Common.time.TimeSpan;
import tds.itemscoringengine.complexitemscorers.IRubricStats;

public class RubricStats implements IRubricStats
{
	private TimeSpan _loadTime = TimeSpan.ZERO;
	private int _numUses;
	private int _numSuccessfulScores;
	private int _numUnsuccessfulScores;
	private TimeSpan _minScoreTime = TimeSpan.MAX_VALUE;
	private TimeSpan _maxScoreTime = TimeSpan.MIN_VALUE;
	private long _totScoreTimeNanos;

	@Override
	public TimeSpan getLoadTime () { return _loadTime; }

	@Override
	public int getNumUses () { return _numUses; }

	@Override
	public int getNumSuccessfulScores () { return _numSuccessfulScores; }

	@Override
	public int getNumUnsuccessfulScores () { return _numUnsuccessfulScores; }


	@Override
	public TimeSpan getMinScoreTime () { return _minScoreTime; }


	@Override
	public long getAveScoreTimeNanos () { return _totScoreTimeNanos / _numUses; }

	@Override
	public TimeSpan getMaxScoreTime () { return _maxScoreTime; }

	@Override
	public void trackScoreTimes (TimeSpan scoreTime) {
		if(scoreTime == null) {
			this._numUnsuccessfulScores++;
			return;
		}
		if (scoreTime.compareTo( _minScoreTime ) < 0 ) _minScoreTime = scoreTime;
		if (scoreTime.compareTo( _maxScoreTime ) > 0 ) _maxScoreTime = scoreTime;
		_totScoreTimeNanos += scoreTime.getDuration ( TimeUnit.NANOSECONDS );
	}

	@Override
	public void trackAccess() {
		this._numUses++;
	}

	@Override
	public void trackLoadTime(TimeSpan loadTime) {
		this._loadTime = loadTime;
	}

}
