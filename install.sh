#!/usr/bin/env sh

## clone project
git clone https://github.com/JimSP/document-propagation.git
cd document-propagation

## pull microservices
sudo docker pull document-propagation-example-server
sudo docker pull document-propagation-example-hello
sudo docker pull document-propagation-example-world

## start microservices
sudo docker-compose up -d

### open browser
x-www-browser http://localhost:8000/swagger-ui.html
