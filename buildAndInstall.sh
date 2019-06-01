#!/usr/bin/env sh

## clone project
git clone https://github.com/JimSP/document-propagation.git
cd document-propagation

## build projects
./gradlew clean build

## run microservices
nohup ./gradlew document-propagation-server:bootRun &
nohup ./gradlew document-propagation-example-hello:bootRun &
nohup ./gradlew document-propagation-example-world:bootRun &

### open browser
x-www-browser http://localhost:8000/swagger-ui.html
