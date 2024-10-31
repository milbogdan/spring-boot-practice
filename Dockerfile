FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY target/demo-0.0.1-SNAPSHOT.jar app.jar
COPY .env /app/.env
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
