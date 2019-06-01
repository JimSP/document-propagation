#!/usr/bin/env sh

## pull microservices
docker pull cafebinario/document-propagation-example-server
docker pull cafebinario/document-propagation-example-hello
docker pull cafebinario/document-propagation-example-world

## start microservices
sudo docker-compose up -d

### open browser
x-www-browser http://localhost:8000/swagger-ui.html
