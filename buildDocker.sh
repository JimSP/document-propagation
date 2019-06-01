#!/usr/bin/env sh

## clone project
git clone https://github.com/JimSP/document-propagation.git
cd document-propagation

## build project
./gradlew document-propagation-server:distDocker
./gradlew document-propagation-example-hello:distDocker
./gradlew document-propagation-example-world:distDocker
