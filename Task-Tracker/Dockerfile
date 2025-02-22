# Use a Maven image to build the application
FROM maven:3.9.6-eclipse-temurin-17 AS builder

# Set working directory inside the container
WORKDIR /app

# Copy pom.xml first (to leverage Docker caching)
COPY pom.xml .

# List files (debugging step)
RUN ls -l /app

# Download dependencies separately to optimize caching
RUN mvn dependency:go-offline

# Copy the entire project (including the src folder)
COPY . .

# List files again (debugging step)
RUN ls -l /app

# Build the application
RUN mvn clean package -DskipTests

# Use a minimal runtime image for running the app
FROM openjdk:17-jdk-slim

# Set working directory inside the container
WORKDIR /app

# Copy the built JAR file from the builder stage
COPY --from=builder /app/target/Task-Tracker-0.0.1-SNAPSHOT.jar app.jar

# Expose the port your Spring Boot app uses
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
