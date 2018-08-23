FROM openjdk:8-jdk-alpine

COPY . /
RUN ./gradlew build

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","build/libs/app.jar"]