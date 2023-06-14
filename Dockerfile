FROM openjdk:20-ea-35-jdk-slim-bullseye


WORKDIR /app

ADD target/*.jar app.jar

EXPOSE 8888

ENTRYPOINT ["java","-jar","app.jar"]