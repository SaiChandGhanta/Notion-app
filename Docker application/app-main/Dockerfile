# syntax=docker/dockerfile:1.2

FROM maven:3.8.6-eclipse-temurin-17-alpine as BUILDER
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:resolve
COPY src /app/src
RUN mvn -f pom.xml package -DskipTests


FROM eclipse-temurin:17-jre-alpine as APPLICATION
COPY --from=BUILDER /app/target/todo-1.0.0-SNAPSHOT.jar /app/todo-1.0.0-SNAPSHOT.jar
ENV dbhost=""
ENV dbport="5432"
ENV db=""
ENV POSTGRES_PASSWORD=""
ENV POSTGRES_USER="postgres"
ENTRYPOINT ["java", "-jar", "/app/todo-1.0.0-SNAPSHOT.jar"]