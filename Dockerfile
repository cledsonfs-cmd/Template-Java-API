FROM maven:3-openjdk-17-slim AS build
ARG MAVEN_PROFILE=dev
COPY ./src /app/src/
COPY ./pom.xml /app
RUN mvn -f ./app/pom.xml clean package -P prod -Dmaven.test.skip -q && chmod +x ./app/target/template-api-0.0.1-SNAPSHOT.jar


FROM openjdk:17.0.2-slim as production
LABEL authors="Cledsonfs"
ENV TZ="America/Fortaleza"
WORKDIR /app

EXPOSE 8081

COPY --from=build /app/target/template-api-0.0.1-SNAPSHOT.jar ./java-api.jar

ENTRYPOINT ["java", "-jar", "java-api.jar"]