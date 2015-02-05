This test code is to run QTI Scoring .NET code in the same way as the Java code.

To be able to run this code follow the below steps:

1) Get a copy of the .NET code. 
2) Open the QTIScoringEngine.sln file in Visual Studio.
2) Import OpenCSV# C# project file into the solution.
3) Import QTITester C# project file into the solution.
4) Make sure everything compiles.
5) Make sure you run the SymPy server.

There is no log4net support in this code. So you will have 
to change a few variables in the code.

There are two ways to run the tests:
1) Run all tests by invoking TestAllFiles from Main() method.
2) Run only a single item test by invoking TestOneFile from Main() method.

Regardless of whichever method you choose to run you will have to set up 
the file where output will be logged by modifying the "LOGFILE" variable in 
Main method.

To run TestAllFiles:

1) Modify the value of the "folder" variable to point to the data folder.
2) Modify the variable "MAXFILES" to fix an upper limmit on how many files you want to test.


To run TestOneFile:

1) Modify the following variables to appropriate paths:

	const String itemId = "10110";
	const String bankId = "NA";
	const String rubricFilePath = "C:/WorkSpace/JavaWorkSpace/TinyScoringEngine/DataFiles/forShiva/ERX/Item_10110_v14.qrx";
	const String responseFile = "C:/WorkSpace/JavaWorkSpace/TinyScoringEngine/DataFiles/forShiva/10110_EQ.tsv";

