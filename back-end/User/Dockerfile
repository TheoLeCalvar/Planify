FROM maven:latest AS maven

WORKDIR /usr/src/app

COPY . /usr/src/app

RUN mvn package


FROM openjdk:17-jdk-alpine

ARG JAR_FILE=User-0.0.1-SNAPSHOT.jar

WORKDIR /opt/app

COPY --from=maven /usr/src/app/target/User-0.0.1-SNAPSHOT.jar /opt/app/

EXPOSE 3202

ENTRYPOINT ["java","-jar","User-0.0.1-SNAPSHOT.jar"]