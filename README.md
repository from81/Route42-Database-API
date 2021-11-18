## About

REST API for providing flexible access to Firebase backend data store.

## Run

### Prerequisite
1. Pull the latest version of our Android app from the `dev` branch.
2. Go to package `com.comp6442.route42.api` and edit `RestApiService.java`.
    1. In the line `String url = (BuildConfig.EMULATOR) ? "http://192.168.0.2:8080/" : "http://13.211.169.204:8080/";` the `192.168.0.2` part must be changed to your local IP address.
    2. If you're using Mac, execute `ifconfig | grep "192.168."` in your terminal, and you will find your local IP address next to the word `inet` in the printed result.
3. You are now ready to run the mobile app and connect it to a REST API running on your machine.

### Run Locally
Run REST API locally on your machine, execute the following command in the terminal (if you're using Mac) inside the project root directory:
```
FIRESTORE_EMULATOR_HOST=localhost:8080; GOOGLE_APPLICATION_CREDENTIALS=./google_application_credentials_route42.json ./gradlew bootRun
```

## Test REST API

Testing GET (Try to test locally instead of using the production api)
```
curl --request GET localhost:8080/post/0092827e-2961-48ed-8995-50adb9f47781
# curl --request GET http://13.211.169.204:8080/post/0092827e-2961-48ed-8995-50adb9f47781
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

