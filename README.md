# Welcome to the Item Scoring Engine

The ItemScoring project is a group of modules that can be used for scoring student responses for different item types. This uses standard QTI response processing with custom operators, an extension point. This engine supports response processing on the following item scoring categories: Trivially scored, Machine scored, and Machine scored with custom operators. Refer to the [custom operators document](http://www.smarterapp.org/documents/Item_Scoring_Custom_Operators.pdf) for more information on this. Instructions to install the customized SymPy library can be found in the docs directory, in the file called `Running Item Scoring Engine.docx`.

## License ##
This project is licensed under the [AIR Open Source License v1.0](http://www.smarterapp.org/documents/American_Institutes_for_Research_Open_Source_Software_License.pdf).

## Getting Involved ##
We would be happy to receive feedback on its capabilities, problems, or future enhancements:

* For general questions or discussions, or issues and bugs, please use the [Forum](http://forum.opentestsystem.org/viewforum.php?f=9).
* Feel free to **Fork** this project and develop your changes!

## 2/11/2015 Release Notes 
Release `R01.00.23-20150211` is a bugfix release of the ItemScoring Engine with an updated, functional EQ scoring engine (see below for installation instructions). Related modifications in Student code have been made in the concurrent Student release. We will be doing frequent periodic releases as we update this functionality.

### Known issues
*TBD*

## Module Overview

### item-scoring-api

   item-scoring-engine contains interfaces which are implemented by the other modules.

### item-scoring-engine

   item-scoring-engine contains scoring logic as well as workers.

### item-scoring-service

   item-scoring-service contains standalone web services to use item-scoring-engine.

### item-scoring-student-simulator

   item-scoring-student-simulator simulates the student site as a thin client. 

### qtiscoringengine

   qtiscoringengine contains QTI scorer module.

### Equation Scoring Service Setup

The Equation (EQ) Scorer is provided as a web service. In order to run this, perform the following steps:

1. On the TDS (student) server, install [Python 2.7](https://www.python.org/download/releases/2.7/), [Sympy 0.7.1](https://github.com/sympy/sympy/releases/tag/sympy-0.7.1), and [Bottle 0.12.8](https://pypi.python.org/pypi/bottle/0.12.8).
1. For security reasons, we recommend creating a user with non-root privileges. Name it, for example, `itemscoringservice`. Switch to this user.
1. Create a directory for the EQ service such as `eq/scripts/` 
1. Create a directory for the EQ service logs such as `eq/logs/` 
1. Install `sympy-scripts/EqScoringWebService.py` into that user's `~itemscoringservice/eq/scripts` for example.
1. Start the service as the `itemscoringservice` user using the following command:
`/usr/bin/python2.7 /home/itemscoringservice/eq/scripts/EqScoringWebService.py  2> /dev/null > /home/itemscoringservice/eq/logs/EqScoringWebService.py.log &`
1. This will ensure the equation scorer runs in the background and saves its log output. 
1. It's recommended to make sure only a single instance of the EQ scorer is running at any one time, and that the logs directory is rotated and pruned regularly.


## Setup
In general, build the code and deploy the JAR file.

### Build order

If building all components from scratch the following build order is needed:

* shared-common
* shared-xml
* shared-web
* scoring-load-test-automation
* shared-threading

## Dependencies
ItemScoring has a number of direct dependencies that are necessary for it to function.  These dependencies are already built into the Maven POM files.

### Compile Time Dependencies

* shared-common
* shared-xml
* shared-web
* scoring-load-test-automation
* shared-threading
* jaxb-osgi
* spring-context
* jcl-over-slf4j
* slf4j-api
* slf4j-log4j12
* log4j
* spring-jdbc
* spring-webmvc
* spring-faces
* myfaces-impl
* jstl
* el-api
* el-impl
* jaxen

### Runtime Dependency
servlet-api

### Test Dependencies
* shared-test
* mockito-core
* junit
* spring-test