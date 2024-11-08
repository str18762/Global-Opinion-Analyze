FROM openjdk:11-jre-slim

WORKDIR /app

COPY target/studySpringboot-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 7001

ENTRYPOINT ["java","-jar","app.jar"]