# Dockerfile for Spring Boot Application
FROM openjdk:21-jdk-slim

# Add application JAR
COPY target/scm.jar /app/scm.jar

# Set working directory
WORKDIR /app

# Expose the default Spring Boot port
EXPOSE 8080

# Run the Spring Boot application
CMD ["java", "-jar", "scm.jar"]
