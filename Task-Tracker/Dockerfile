# Use an official Java runtime as the base image
FROM eclipse-temurin:17 AS builder

# Set working directory
WORKDIR /app

# Copy all project files
COPY . .

# Ensure Maven Wrapper is executable
RUN chmod +x mvnw

# Build the application
RUN ./mvnw clean package -DskipTests

# Use a minimal runtime image
FROM eclipse-temurin:17 AS runtime

WORKDIR /app

# Copy only the built JAR from the builder stage
COPY --from=builder /app/target/*.jar app.jar

# Expose the application port (change if needed)
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "app.jar"]
