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


FROM maven:3.8.5-openjdk-21 AS build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:21.0.1-jdk-slim

COPY --from=build /target/scm-0.0.1-SNAPSHOT.jar scm.jar 
EXPOSE 8080
ENTRYPOINT ["java","-jar","scm.jar"]