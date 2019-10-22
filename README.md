# How to run
There are two ways to run the app: 
1) via docker (and docker-compose)
2) or locally with Gradle and npm

App consists of backend (port 8080) and frontend (port 3000). Ports are hardcoded, so please ensure nothing else is listening on those ports before you start the app. 

### To run it using Docker:
Navigate to project root folder and execute:
```shell script
docker-compose up
```

Note: docker images are there in order to run the app without needing to install other dependencies and not intended for active development (there are no shared mounts, no cached gradle, no multi-stage builds, etc.)

### To run it locally
Go to project and compile and start backend:
```shell script
./gradlew bootRun
```
Go to frontend sub folder, install npm dependencies and run frontend
```shell script
cd frontend/
npm i
npm start
``` 


### Open in browser
Once the app is up and running, point your browser to http://localhost:3000


### To run tests
Via docker - execute gradle task in running container
```shell script
docker-compose exec backend /opt/challenge/gradlew test
```

Locally:
```shell script
./gradlew test
```

