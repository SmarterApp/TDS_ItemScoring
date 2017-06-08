################
# Docker file to build a docker servlet container hosting the Item Scoring Service WAR.
################

#Based off of the shared secure tomcat 7 image from SS_SharedMultiJar
FROM fwsbac/secure-tomcat-image:3.0.2

#Add the WAR file
ADD item-scoring-service-*.war $CATALINA_HOME/webapps/itemscoring.war

EXPOSE 8080
CMD ["catalina.sh", "run"]