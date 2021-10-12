#!/usr/bin/env bash

KEY="route42.pem"
CREDS="google_application_credentials_route42.json"
AWS_USERNAME="ec2-user"
AWS_HOSTNAME="ec2-13-211-169-204.ap-southeast-2.compute.amazonaws.com"

rm -rf build/
echo "Deleted build/ folder"

./gradlew bootJar
echo "Generating jar file"

scp -i $KEY build/libs/Route42RestAPI-1.0-SNAPSHOT.jar ${AWS_USERNAME}@${AWS_HOSTNAME}:/home/ec2-user
echo "Copied jar file from local machine to ec2 instance"

scp -i $KEY execute_commands_ec2.sh ${AWS_USERNAME}@${AWS_HOSTNAME}:/home/ec2-user
echo "Copied latest startup script from local machine to ec2 instance"

scp -i $KEY $CREDS ${AWS_USERNAME}@${AWS_HOSTNAME}:/home/ec2-user/google_application_credentials_route42.json
echo "Copied google credentials from local machine to ec2 instance"

echo "Connecting to ec2 instance and starting server using java -jar command"
ssh -i $KEY ec2-user@${AWS_HOSTNAME} ./execute_commands_ec2.sh
