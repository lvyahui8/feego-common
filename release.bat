@echo off
mvn -pl !feego-common-example-start,!feego-common-example-service clean install deploy -P release