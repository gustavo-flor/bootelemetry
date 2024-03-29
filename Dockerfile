FROM maven:3.8.5-openjdk-17-slim AS build

COPY pom.xml .
COPY src src

RUN mvn clean install --batch-mode -DskipTests

FROM openjdk:17-slim AS release

COPY --from=build /target/*.jar /app.jar
COPY docker-entrypoint.sh /
COPY otel/otel-javaagent.jar /

RUN chmod +x /docker-entrypoint.sh

ENTRYPOINT ["/docker-entrypoint.sh"]