FROM openjdk:8
EXPORT 8081
ADD target/taskmanager-docker.jar taskmanager-docker.jar
ENTRYPOINT ["java","-jar","/taskmanager-docker.jar"]
