#!/bin/sh

docker compose -f common.yml -f zookeeper.yml up

docker compose -f common.yml -f kafka.yml up

docker compose -f common.yml -f init-topics.yml up