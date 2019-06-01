#!/usr/bin/env sh

## build project
./gradlew clean
./gradlew document-propagation-server:distDocker --stacktrace
./gradlew document-propagation-example-hello:distDocker --stacktrace
./gradlew document-propagation-example-world:distDocker --stacktrace
