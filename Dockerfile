# Dockerfile for Spring Boot Application
# FROM openjdk:21-jdk-slim

# # Add application JAR
# COPY target/scm.jar /app/scm.jar

# # Set working directory
# WORKDIR /app

# # Expose the default Spring Boot port
# EXPOSE 8080

# # Run the Spring Boot application
# CMD ["java", "-jar", "scm.jar"]

# Stage 1: Build the application using Maven and JDK 21
FROM maven:3.8.5-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Stage 2: Use a slim OpenJDK 21 image to run the app
FROM openjdk:21-slim
WORKDIR /app
COPY --from=build /app/target/scm-0.0.1-SNAPSHOT.jar scm.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "scm.jar"]
