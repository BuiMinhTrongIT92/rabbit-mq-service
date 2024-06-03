FROM eclipse-temurin:17-jre
MAINTAINER trongbui
WORKDIR /app
COPY target/rabbit-mq-service-0.0.1-SNAPSHOT.jar /rabbit-mq-service-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/rabbit-mq-service-0.0.1-SNAPSHOT.jar"]