#!/usr/bin/env bash

kill $(lsof -t -i)
echo "Killed process running"

GOOGLE_APPLICATION_CREDENTIALS="/home/ec2-user/application_default_credentials.json" nohup java -jar Route42RestAPI-1.0-SNAPSHOT.jar &
echo "Started server using java -jar command"
