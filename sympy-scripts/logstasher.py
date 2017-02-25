import os
import logging
from logging import config

import logstash


LOGSTASH_ENV_VARNAME = 'LOGSTASH_DESTINATION'

LOGGING = {
    'version': 1,
    'formatters': {
        'simple': {
            'format': '[%(levelname)s] %(message)s [%(module)s.%(funcName)s:%(lineno)d]'
        },
    },
    'handlers': {
        'console': {
            'level':'DEBUG',
            'class':'logging.StreamHandler',
            'formatter':'simple',
        }
    },
    'loggers': {
        'develop': {
            'handlers': ['console'],
            'level': 'DEBUG',
            'propagate': True,
        },
    },
}

def getLogger():
    config.dictConfig(LOGGING)
    logger = logging.getLogger('develop')
        
    # Enable logstash by setting env var as follows:
    # $ export LOGSTASH_DESTINATION=logstash.sbtds.org:4560
    if LOGSTASH_ENV_VARNAME not in os.environ:
        print("Env var '%s' not set. Logging to stdout." % LOGSTASH_ENV_VARNAME)
    else:
        print("Env var '%s' is '%s'." % (LOGSTASH_ENV_VARNAME, os.environ[LOGSTASH_ENV_VARNAME]))
        (logstash_host, logstash_port) = os.environ[LOGSTASH_ENV_VARNAME].split(':')
        logstash_port = int(logstash_port)
        # TODO: connect to logstash server and complain if it isn't up
        print("Logging to logstash at %s:%d" % (logstash_host, logstash_port))
        logger.addHandler(logstash.TCPLogstashHandler(logstash_host, logstash_port, version=1))
    return logger
