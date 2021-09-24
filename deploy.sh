#!/usr/bin/env bash

KEY="route42.pem"
CREDS="-firebase-adminsdk-5s99m-cb8b011821.json"
AWS_USERNAME="ec2-user"
AWS_HOSTNAME="ec2-13-211-169-204.ap-southeast-2.compute.amazonaws.com"

rm -rf build/
echo "Deleted build/ folder"

./gradlew bootJar
echo "Generating jar file"

#Copy execute_commands_on_ec2.sh file which has commands to be executed on server... Here we are copying this file
# every time to automate this process through 'deploy.sh' so that whenever that file changes, it's taken care of
scp -i $KEY execute_commands_on_ec2.sh ${AWS_USERNAME}@${AWS_HOSTNAME}:/home/ec2-user
echo "Copied latest 'execute_commands_on_ec2.sh' file from local machine to ec2 instance"

scp -i $KEY build/libs/Route42RestAPI-1.0-SNAPSHOT.jar ${AWS_USERNAME}@${AWS_HOSTNAME}:/home/ec2-user
echo "Copied jar file from local machine to ec2 instance"

scp -i $KEY "$CREDS" ${AWS_USERNAME}@${AWS_HOSTNAME}:temp.json
echo "Copied google credentials from local machine to ec2 instance"

#echo "Connecting to ec2 instance and starting server using java -jar command"
#ssh -i "route42.pem" ec2-user@ec2-13-211-169-204.ap-southeast-2.compute.amazonaws.com ./execute_commands_ec2.sh
