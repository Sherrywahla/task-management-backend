# Use an official Java runtime as the base image
FROM eclipse-temurin:17 AS builder

# Set working directory
WORKDIR /app

# Copy all project files
COPY . .

# Ensure the Maven Wrapper exists before making it executable
RUN if [ -f mvnw ]; then chmod +x mvnw; else echo "mvnw not found, failing build"; exit 1; fi

# Build the application using Maven Wrapper if available, otherwise system Maven
RUN ./mvnw clean package -DskipTests || mvn clean package -DskipTests

# Use a minimal runtime image
FROM eclipse-temurin:17 AS runtime

WORKDIR /app

# Copy only the built JAR from the builder stage
COPY --from=builder /app/target/*.jar app.jar

# Expose the application port
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "app.jar"]
