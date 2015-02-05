using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Text.RegularExpressions;
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
    class Program
    {
        static void Main(string[] args)
        {
            const String LOGFILE = @"C:\WorkSpace\JavaWorkSpace\TinyScoringEngine\logs\qtitester.net.log";
            try
            {
                using (Logger logger = new Logger(LOGFILE))
                {
                    //Uncomment TestAllFiles to run all tests.
                    TestAllFiles(args, logger);
                    //Uncomment TestOneFile to run tests against a specific file.
                    TestOneFile(args, logger);
                }
            }
            catch (Exception exp)
            {
                Console.WriteLine(exp.StackTrace);
                Console.WriteLine(exp.Message);
            }
        }


        private static void TestAllFiles(String[] args, Logger _logger)
        {
            ScoreCounter scoreCounter = new ScoreCounter();
            try
            {
                const int MAXFILES = 1000;
                const String folder = "C:/WorkSpace/JavaWorkSpace/TinyScoringEngine/DataFiles/forShiva/";
                Dictionary<String, String> rubricsMap = new Dictionary<String, String>();

                MapRubricPathToItemId(folder, rubricsMap);

                int fileCounter = 1;
                foreach (FileInfo file in new DirectoryInfo(folder).EnumerateFiles())
                {
                    if (String.Equals(file.FullName, ".tsv"))
                    {

                        if (fileCounter == MAXFILES)
                            break;
                        ++fileCounter;

                        String itemId = GetItemIdForResponsefile(file.FullName);
                        if (String.IsNullOrEmpty(itemId))
                            continue;

                        String rubricPath = rubricsMap[itemId];

                        _logger.Info(String.Format("Processing input file {0} with item id {1} using rubric {2}.", file.FullName, itemId, rubricPath));

                        FormQtiTester qtiTester = new FormQtiTester(new ItemSpecification
                        {

                            _itemId = itemId,
                            _bankId = "NA",
                            _rubricFilePath = rubricPath
                        }, scoreCounter, _logger);

                        qtiTester.ProcessResponseFiles(file.FullName);
                    }
                }

            }
            catch (Exception exp)
            {
                Console.WriteLine(exp.StackTrace);
                _logger.Error(exp.Message, exp);
            }

            _logger.Info(scoreCounter.ToString());
        }

        public static void TestOneFile(String[] args, Logger _logger)
        {
            ScoreCounter scoreCounter = new ScoreCounter();
            try
            {
                // From here: http://blog.frankel.ch/thoughts-on-java-logging-and-slf4j
                const String itemId = "10110";
                const String bankId = "NA";
                const String rubricFilePath = "C:/WorkSpace/JavaWorkSpace/TinyScoringEngine/DataFiles/forShiva/ERX/Item_10110_v14.qrx";
                const String responseFile = "C:/WorkSpace/JavaWorkSpace/TinyScoringEngine/DataFiles/forShiva/10110Line2.tsv";

                FormQtiTester qtiTester = new FormQtiTester(new ItemSpecification
                {
                    _itemId = itemId,
                    _bankId = bankId,
                    _rubricFilePath = rubricFilePath
                }, scoreCounter, _logger);

                qtiTester.ProcessResponseFiles(responseFile);

            }
            catch (Exception exp)
            {
                Console.WriteLine(exp.StackTrace);
                _logger.Error(exp.Message, exp);
            }
            _logger.Info(scoreCounter.ToString());
        }

        const String FilePattern = "(?<itemid>[0-9]*)_[a-zA-Z]*\\.tsv";

        private static String GetItemIdForResponsefile(String file)
        {
            Match m = Regex.Match(file.ToLower(), FilePattern);
            if (m.Success)
            {
                return m.Groups["itemid"].Value;
            }
            return null;
        }

        const String RubricPattern = "item_(?<itemid>[0-9]*)_v[0-9]*\\.qrx";

        private static void MapRubricPathToItemId(String folderPathToScan, Dictionary<String, String> outputMap)
        {
            // scan files.
            if (File.Exists(folderPathToScan))
                MapRubricPathToItemIdForFile(folderPathToScan, outputMap);
            else
            {
                foreach (FileInfo subFile in new DirectoryInfo(folderPathToScan).EnumerateFiles())
                {
                    MapRubricPathToItemIdForFile(subFile.FullName, outputMap);
                }

                foreach (DirectoryInfo subFolder in new DirectoryInfo(folderPathToScan).EnumerateDirectories())
                {
                    MapRubricPathToItemId(subFolder.FullName, outputMap);
                }
            }
        }

        private static void MapRubricPathToItemIdForFile(String file, Dictionary<String, String> map)
        {

            FileInfo folder = new FileInfo(file);

            String folderName = folder.Name.ToLower();

            Match matcher = Regex.Match(folderName, RubricPattern);
            if (matcher.Success)
            {
                Group grp = matcher.Groups["itemid"];
                map[grp.Value] = folder.FullName;
            }
        }
    }
}
