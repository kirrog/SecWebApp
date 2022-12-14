# syntax=docker/dockerfile:1

FROM maven:3.8.1-jdk-11-slim AS build
WORKDIR secapp/
COPY ./pom.xml pom.xml
COPY ./src src/
RUN mvn clean package -DskipTests

FROM eclipse-temurin:11-alpine
COPY --from=build secapp/target/ ./
EXPOSE 5432 8081
ENTRYPOINT ["java", "-jar", "demoproject-0.0.1-SNAPSHOT.jar"]
