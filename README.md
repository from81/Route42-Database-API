## Getting started

Make sure you have the following files

- /settings.gradle
- /-firebase-adminsdk-5s99m-cb8b011821.json

Warning

- Unlike the client side app, this REST API by default does not use the emulator (because I haven't configured it to use the emulator).
- For hashtag queries, the API runs an ArrayContains for each individual hashtag, and performs a union over all results. 
  - Please don't pass large amount of hashtags. For now, I've set a limit to 10 hashtags.
  - But still, best not to use it too much so that we don't incur costs for the cloud infrastructure.

## AWS EC2 

Public IPv4 DNS: `ec2-13-211-169-204.ap-southeast-2.compute.amazonaws.com`
Public IPv4 address: `13.211.169.204`

## Testing Endpoints

> You can only test the REST API locally. To deploy, create a merge request and owner of the repository will merge your branch once the merge request is approved.

Run locally:
```
GOOGLE_APPLICATION_CREDENTIALS=-firebase-adminsdk.json ./gradlew bootRun
```

Testing GET

```
curl --request GET localhost:8080/post/0092827e-2961-48ed-8995-50adb9f47781
curl --request GET http://13.211.169.204:8080/post/0092827e-2961-48ed-8995-50adb9f47781
```

Testing POST request
```
curl --header "Content-Type: application/json" \
    --request POST \
    --data '{"query":"username: moniquechan"}' \
    localhost:8080/search

curl --header "Content-Type: application/json" \
    --request POST \
    --data '{"query":"username: moniquechan and hashtags: #love #instarunners #triathlon"}' \
    localhost:8080/search
 
curl --header "Content-Type: application/json" \
    --request POST \
    --data '{"query":"(username: moniquechan and hashtags: #love #instarunners #triathlon) or username: miranda45"}' \
    localhost:8080/search

curl --header "Content-Type: application/json" \
    --request POST \
    --data '{"query":"(username: moniquechan and hashtags: #love #instarunners #triathlon) or username: miranda45"}' \
    http://13.211.169.204:8080/search
```
