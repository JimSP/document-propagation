#!/usr/bin/env sh

## publish oss.sonatype
./gradlew clean build
./gradlew document-propagation-commum:uploadArchives
./gradlew document-propagation-client:uploadArchives
./gradlew document-propagation-spring-boot-auto-configure:uploadArchives
./gradlew document-propagation-spring-boot-starter:uploadArchives
