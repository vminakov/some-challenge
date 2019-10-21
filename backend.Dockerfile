FROM openjdk:12-jdk-alpine

WORKDIR /opt/challenge
COPY ./ /opt/challenge
RUN /opt/challenge/gradlew build

EXPOSE 8080
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/opt/challenge/build/libs/challenge-0.0.1-SNAPSHOT.jar"]