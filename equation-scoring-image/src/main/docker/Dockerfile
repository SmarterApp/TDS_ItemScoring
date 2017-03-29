################
# Docker file to build a python container hosting the equation scoring service.
################

#Based off of the alpine python container
FROM python:2.7-alpine

#Add the local resources
ADD site-packages.zip /site-packages.zip
ADD EqScoringWebService.py /EqScoringWebService.py
ADD logstasher.py /logstasher.py
ADD requirements.txt /requirements.txt

#Install required tools
RUN apk add --update \
    bash \
  && rm -rf /var/cache/apk/*
RUN pip install -r /requirements.txt

#Install custom AIR sympy
RUN unzip site-packages.zip -o -d /usr/local/lib/python2.7/site-packages/

#Create log directory
RUN mkdir /logs && chmod a+w /logs

EXPOSE 8080
CMD ["python", "/EqScoringWebService.py"]