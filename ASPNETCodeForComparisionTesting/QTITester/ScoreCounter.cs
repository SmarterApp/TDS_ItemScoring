using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace QTITester
{

    enum LogType
    {
        correct, incorrect, error
    };

    public class ScoreCounter
    {
        private int _incorrect = 0;
        private int _correct = 0;
        private int _exception = 0;

        private Dictionary<LogType, Dictionary<int, int>> _histogram = new Dictionary<LogType, Dictionary<int, int>>();

        public void IncrementIncorrect(int realScore)
        {
            lock (this)
            {
                ++_incorrect;
                AddToMapForRealScore(realScore, LogType.incorrect);
            }
        }

        public void IncrementCorrect(int realScore)
        {
            lock (this)
            {
                ++_correct;
                AddToMapForRealScore(realScore, LogType.correct);
            }
        }

        public void IncrementErrors(int realScore)
        {
            lock (this)
            {
                ++_exception;
                AddToMapForRealScore(realScore, LogType.error);
            }
        }

        public int GetIncorrect()
        {
            return _incorrect;
        }

        public int GetCorrect()
        {
            return _correct;
        }

        public int GetErrors()
        {
            return _exception;
        }

        public override String ToString()
        {
            StringBuilder outputBuilder = new StringBuilder();
            outputBuilder.Append("======================Stats====================\n");
            outputBuilder.Append(String.Format("Correct: {0} ; Incorrect: {1} ; Errors : {2}\n", this.GetCorrect(), this.GetIncorrect(), this.GetErrors()));

            foreach (KeyValuePair<LogType, Dictionary<int, int>> entries in _histogram)
            {
                LogType key = entries.Key;
                foreach (KeyValuePair<int, int> record in entries.Value)
                {
                    outputBuilder.Append(String.Format("Type: {0} ; Score : {1} ; Counter : {2}\n", key, record.Key, record.Value));
                }
            }

            return outputBuilder.ToString();
        }

        /*
         * for type : correct, incorrect or error we want to keep track of the
         * distribution of scores.
         */
        private void AddToMapForRealScore(int realScore, LogType type)
        {
            if (!_histogram.ContainsKey(type))
                _histogram[type] = new Dictionary<int, int>();

            Dictionary<int, int> scoreHistogram = _histogram[type];

            int scoreTracker = 0;
            if (scoreHistogram.ContainsKey(realScore))
                scoreTracker = scoreHistogram[realScore];

            scoreTracker++;
            scoreHistogram[realScore] = scoreTracker;
        }
    }

}
