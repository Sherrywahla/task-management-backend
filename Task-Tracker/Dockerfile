# Use a Maven image with Java 17
FROM maven:3.8.8-eclipse-temurin-17 AS builder

# Set working directory inside the container
WORKDIR /app

# Copy the project files
COPY . .

# Build the application using Maven
RUN mvn clean package -DskipTests

# Use a minimal runtime image
FROM openjdk:17-jdk-slim

# Set working directory inside the container
WORKDIR /app

# Copy the built JAR file from the builder stage
COPY --from=builder /app/target/Task-Tracker-0.0.1-SNAPSHOT.jar app.jar

# Expose the port your Spring Boot app uses
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
