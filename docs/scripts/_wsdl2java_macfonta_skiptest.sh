#!/bin/sh

source ./_env.sh

export MVN_PROFILE=_wsdl2java
export ROOT_DIR_ANGULAR_FO=/opt/angular/csi/workspace_fpabil/fpabil-fpabilwcl/dist/fpabil-fpabilwcl
export ROOT_DIR_ANGULAR_BO=/opt/angular/csi/workspace_fpabil/fpabil-fpabilbowlc/dist/fpabil-fpabilbowlc

java -version

COMMAND="mvn -f ../../pom.xml -s $MVN_SETTINGS -Dmaven.test.skip=true -P$MVN_PROFILE -Droot.dir.angular.fo=$ROOT_DIR_ANGULAR_FO -Droot.dir.angular.bo=$ROOT_DIR_ANGULAR_BO clean generate-sources"
#COMMAND="mvn -f ../../pom.xml axistools:wsdl2java"



echo "$COMMAND" 

exec $COMMAND 












