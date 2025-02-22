# Use a Maven image to build the application
FROM maven:3.9.6-eclipse-temurin-17 AS builder

# Set working directory inside the container
WORKDIR /app

# Copy necessary files first
COPY pom.xml .
COPY mvnw .
COPY mvnw.cmd .
COPY .mvn .mvn  # If you have a .mvn folder, copy it too

# Give execute permissions to Maven wrapper
RUN chmod +x mvnw

# Copy the rest of the application
COPY src src

# Build the application
RUN ./mvnw clean package -DskipTests

# Use a minimal runtime image for running the app
FROM openjdk:17-jdk-slim

WORKDIR /app

# Copy the built JAR file
COPY --from=builder /app/target/Task-Tracker-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
