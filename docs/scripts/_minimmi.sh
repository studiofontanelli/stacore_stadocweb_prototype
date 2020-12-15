#!/bin/sh

source ./_env.sh


java -version


java -version

mvn -f ../../pom.xml -s $MVN_SETTINGS dependency:purge-local-repository

