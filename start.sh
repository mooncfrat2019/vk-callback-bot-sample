#!/usr/bin/env sh

docker rm -f "$(docker ps -a -q)"
docker build -t kotlin-vk-bot .
docker run -dp 5000:5000 kotlin-vk-bot --env-file .env
