using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Text;
using System.Threading.Tasks;
using System.ComponentModel;
using System.Data;
using System.IO;
using System.Xml;
using Ast.OpenCsv;
using TinyCtrlScoringEngine;
using TinyEquationScoringEngine;
using TinyGRScoringEngine;
using TinyTableScoringEngine;
using QTIScoringEngine;

namespace QTITester
{
    public class FormQtiTester
    {
        private Logger _logger = null;
        private ItemSpecification _itemSpec;
        private QTIRubric _rubric;
        private Dictionary<String, String> _responseValues = new Dictionary<String, String>();
        private ValidationLog _log = null;
        private ScoreCounter _counter;

        public FormQtiTester(ItemSpecification itemSpec, ScoreCounter scoreCounter, Logger logger)
        {
            _counter = scoreCounter;
            _itemSpec = itemSpec;
            this._logger = logger;
            _log = new ValidationLog(_itemSpec._rubricFilePath);
            // load the rubric.
            XmlReaderSettings settings = new XmlReaderSettings();
            try
            {
                using (XmlReader reader = XmlReader.Create(new StreamReader(_itemSpec._rubricFilePath)))
                {
                    _rubric = QTIRubric.FromXML(_itemSpec._rubricFilePath, reader, _log);
                    if (!_rubric.Validate(_log))
                    {
                        StringBuilder rationale = new StringBuilder();

                        for (int i = 0; i < _log.Count; i++)
                            rationale.Append(_log.Message(i) + "\n");

                        String message = "Rubric validation failed : " + rationale.ToString();
                        _logger.Error(message);
                        throw new Exception(message);
                    }
                }
            }
            catch (Exception exp)
            {
                _logger.Error(exp.Message, exp);
                Console.Error.WriteLine(exp.StackTrace);
                throw new Exception("Unable to load rubric", exp);
            }

            _responseValues.Clear();
            foreach (String[] info in _rubric.PublicVariableDeclarations)
            {
                String val = GetValue(info[0]);
                _responseValues[info[0]] = val;
            }
            DisplayVariables();
        }


        public void ProcessResponseFiles(String fileName)
        {
            int lineCounter = 1;
            try
            {
                using (StreamReader bfr = File.OpenText(fileName))
                {
                    // get the header line.
                    String line = bfr.ReadLine();
                    // process from next line onwards.
                    while ((line = bfr.ReadLine()) != null)
                    {
                        ++lineCounter;
                        // get the columns.
                        DataRow columns = CsvReader.Parse(new StringReader(line), false, '~').Rows[0];

                        String itemId = columns[0].ToString();
                        int originalScore = int.Parse(columns[1].ToString());
                        String response = columns[2].ToString();

                        if (!String.Equals(itemId, _itemSpec._itemId))
                            continue;

                        Dictionary<String, String> cloneResponseValues = new Dictionary<String, String>();
                        // TODO Shiva: I do not think I need to clone _responseValues map as it
                        // should be empty at this point but
                        // I will just do this.
                        foreach (String key in _responseValues.Keys)
                        {
                            cloneResponseValues[key] = _responseValues[key];
                        }
                        cloneResponseValues["RESPONSE"] = response;
                        try
                        {
                            List<String[]> bindings = _rubric.Evaluate(cloneResponseValues);

                            String scorestring = FindValue(bindings, "SCORE");
                            int score = -1;
                            int.TryParse(scorestring, out score);
                            if (score == originalScore)
                            {
                                _counter.IncrementCorrect(originalScore);
                                _logger.Info(String.Format("Correct, item={0}, line={2}, score={1}", itemId, score, lineCounter));
                            }
                            else
                            {
                                _counter.IncrementIncorrect(originalScore);
                                _logger.Info(String.Format("Wrong, item={0}, line={3}, score={1}, realScore={2}", itemId, score, originalScore, lineCounter));
                            }
                        }
                        catch (Exception exp)
                        {
                            _counter.IncrementErrors(originalScore);
                            _logger.Info(String.Format("Exception, item={0}, line={2}, message={1}", itemId, exp.Message, lineCounter));
                        }
                    }
                }
            }
            catch (Exception exp)
            {
                Console.WriteLine(exp.StackTrace);
                _logger.Info(String.Format("Exception processing file file={0}, line={2}, message={1}", fileName, exp.Message, lineCounter));
            }
        }


        private void DisplayVariables()
        {
            _logger.Info("==========Start displaying public variable declarations=============");
            List<String[]> variableInfo = _rubric.PublicVariableDeclarations;
            foreach (String[] info in variableInfo)
            {
                String[] stuff = new String[4];
                stuff[0] = info[0];
                stuff[1] = info[1];
                stuff[2] = info[2];

                String val = GetValue(info[0]);
                stuff[3] = (val == null) ? "<null>" : val;
                _logger.Info("[" + String.Join(",", stuff) + "]");
            }
            _logger.Info("==========End displaying public variable declarations=============");
        }

        private void DisplayReturnVariables(List<String[]> variableInfo)
        {
            _logger.Info("==========Start displaying return variable declarations=============");
            foreach (String[] info in variableInfo)
            {
                String[] stuff = new String[4];
                stuff[0] = info[0];
                stuff[1] = info[1];
                stuff[2] = info[2];
                stuff[3] = info[3];
                _logger.Info("[" + String.Join(",", stuff) + "]");
            }
            _logger.Info("==========End displaying return variable declarations=============");
        }

        private String GetValue(String p)
        {
            if (_responseValues.ContainsKey(p))
                return _responseValues[p];
            return null;
        }

        private String FindValue(List<String[]> bindings, String p)
        {
            foreach (String[] info in bindings)
            {
                if (String.Equals(info[0], p))
                    return info[3];
            }
            return "";
        }

        static FormQtiTester()
        {
            CustomOperatorRegistry.Instance.AddOperatorFactory(new TinyGRCustomOpFactory());
            CustomOperatorRegistry.Instance.AddOperatorFactory(new CtrlCustomOpFactory());
            CustomOperatorRegistry.Instance.AddOperatorFactory(new TinyTableCustomOpFactory());
            CustomOperatorRegistry.Instance.AddOperatorFactory(new TinyEqCustomOpFactory(new Uri("http://localhost:8080/"), 3));
        }

    }
}

