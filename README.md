# Welcome to the ItemScoringDev Application

The ItemScoringDev project is a group of modules that can be used for scoring student responses for different item types.


## License ##
This project is licensed under the [AIR Open Source License v1.0](http://www.smarterapp.org/documents/American_Institutes_for_Research_Open_Source_Software_License.pdf).

## Getting Involved ##
We would be happy to receive feedback on its capabilities, problems, or future enhancements:

* For general questions or discussions, please use the [Forum](forum_link_here).
* Use the **Issues** link to file bugs or enhancement requests.
* Feel free to **Fork** this project and develop your changes!

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

   qtiscoringengine contains qti scorer module.



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
ItemScoringDev has a number of direct dependencies that are necessary for it to function.  These dependencies are already built into the Maven POM files.

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