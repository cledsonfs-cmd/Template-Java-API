FROM maven:3-openjdk-17-slim AS build
ARG MAVEN_PROFILE=dev
COPY ./src /app/src/
COPY ./pom.xml /app
RUN mvn -f ./app/pom.xml clean package -P${MAVEN_PROFILE} -Dmaven.test.skip && chmod +x ./app/target/api-java-1.0-SNAPSHOT.jar

LABEL authors="Cledsonfs"

WORKDIR /app

EXPOSE 8081

COPY --from=build /app/target/api-java-1.0-SNAPSHOT.jar ./api-java.jar

ENTRYPOINT ["java", "-jar", "api-java.jar"]