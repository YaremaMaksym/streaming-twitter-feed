#!/bin/bash

docker-compose up -d crdb-1 crdb-2

docker-compose exec crdb-1 ./cockroach init --insecure
docker-compose exec crdb-1 ./cockroach sql --insecure --execute="CREATE DATABASE twt_feed_db;"

docker-compose up -d