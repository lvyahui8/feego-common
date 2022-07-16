#!/bin/bash
#  mvn versions:set -DnewVersion="1.2.0"
mvn -pl !feego-common-example-start,\
!feego-common-example-service \
!feego-common-example-api \
!feego-common-example-api-starter \
!feego-common-example-client \
clean install deploy -P release