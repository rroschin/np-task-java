FROM openjdk:11-jdk-oracle
VOLUME /tmp
COPY target/job-task-1.0.0.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
