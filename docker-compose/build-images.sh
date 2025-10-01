#!/bin/sh

cd ../admin || exit
#mvn clean install -Dmaven.test.skip=true
mvn compile jib:dockerBuild -Dmaven.test.skip=true || exit

cd ../attachments || exit
#mvn clean install -Dmaven.test.skip=true
mvn compile jib:dockerBuild -Dmaven.test.skip=true || exit

cd ../auctions || exit
#mvn clean install -Dmaven.test.skip=true
mvn compile jib:dockerBuild -Dmaven.test.skip=true || exit

cd ../emails || exit
#mvn clean install -Dmaven.test.skip=true
mvn compile jib:dockerBuild -Dmaven.test.skip=true || exit

cd ../configserver || exit
#mvn clean install -Dmaven.test.skip=true
mvn compile jib:dockerBuild -Dmaven.test.skip=true || exit

cd ../eurekaserver || exit
#mvn clean install -Dmaven.test.skip=true
mvn compile jib:dockerBuild -Dmaven.test.skip=true || exit

cd ../gatewayserver || exit
#mvn clean install -Dmaven.test.skip=true
mvn compile jib:dockerBuild -Dmaven.test.skip=true || exit