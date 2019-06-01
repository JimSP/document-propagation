#!/usr/bin/env sh

## build project
./gradlew document-propagation-server:distDocker
./gradlew document-propagation-example-hello:distDocker
./gradlew document-propagation-example-world:distDocker
