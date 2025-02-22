# Use an official OpenJDK image
FROM openjdk:17-jdk-slim

# Set working directory inside the container
WORKDIR /app

# Copy the JAR file into the container
COPY target/Task-Tracker-0.0.1-SNAPSHOT.jar app.jar


# Expose the port your Spring Boot app uses
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
