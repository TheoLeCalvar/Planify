FROM maven:latest AS maven

WORKDIR /usr/src/app

COPY . /usr/src/app

RUN mvn package


FROM openjdk:17-jdk-alpine

ARG JAR_FILE=Solver-0.0.1-SNAPSHOT.jar

WORKDIR /opt/app

COPY --from=maven /usr/src/app/target/Solver-0.0.1-SNAPSHOT.jar /opt/app/

EXPOSE 3201

ENTRYPOINT ["java","-Xmx3g","-jar","Solver-0.0.1-SNAPSHOT.jar"]