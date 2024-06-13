FROM maven:3-openjdk-17-slim AS build
ARG MAVEN_PROFILE=dev
COPY ./src /app/src/
COPY ./pom.xml /app
RUN mvn -f ./app/pom.xml clean package -P${MAVEN_PROFILE} && chmod +x ./app/target/Template-API-java-1.0-SNAPSHOT.jar

FROM openjdk:17.0.2-slim as production
LABEL authors="Cledsonfs"

WORKDIR /app

EXPOSE 8081

COPY --from=build /app/target/Template-API-java-1.0-SNAPSHOT.jar ./java-api.jar

ENTRYPOINT ["java", "-jar", "java-api.jar"]