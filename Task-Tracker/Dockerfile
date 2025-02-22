# Use a Maven image to build the application
FROM maven:3.8.8-openjdk-17 AS builder

# Set working directory inside the container
WORKDIR /app

# Copy the project files
COPY . .

# Ensure the Maven wrapper has execute permissions
RUN chmod +x mvnw

# Build the application
RUN ./mvnw clean package -DskipTests

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
