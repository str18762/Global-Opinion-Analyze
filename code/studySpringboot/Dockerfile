FROM joengenduvel/jre17:latest

WORKDIR /app

COPY target/studySpringboot-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 7001

# 启动命令，这里指定了spring.profiles.active=docker，以便在docker环境下使用docker profile
ENTRYPOINT ["java","-jar","app.jar","--spring.profiles.active=docker"]