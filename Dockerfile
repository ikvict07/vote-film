FROM gradle:8.5-jdk21 AS build


WORKDIR /app

COPY . .


RUN gradle clean bootJar


FROM openjdk:21-jdk-slim


COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app.jar"]