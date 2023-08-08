# syntax=docker/dockerfile:1

FROM eclipse-temurin:17-jdk-jammy as base
WORKDIR /wanted
COPY gradle/ gradle
COPY settings.gradle gradlew build.gradle ./
RUN ./gradlew build --refresh-dependencies
COPY src ./src

FROM base as development
CMD ["./gradlew", "bootRun"]

FROM base as build
RUN ./gradlew build

FROM eclipse-temurin:17-jre-jammy as production
COPY --from=build /wanted/build/libs/*SNAPSHOT.jar /app.jar
CMD ["java", "-jar", "-Dspring.profiles.active=prod","/app.jar"]