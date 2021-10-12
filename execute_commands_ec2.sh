#!/usr/bin/env bash

kill $(lsof -t -i)
echo "Killed process running"

GOOGLE_APPLICATION_CREDENTIALS=./google_application_credentials_route42.json nohup java -jar Route42RestAPI-1.0-SNAPSHOT.jar &
echo "Started server using java -jar command"
