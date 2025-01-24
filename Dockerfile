# Dockerfile for Spring Boot Application
FROM openjdk:21-jdk-slim

# Set application-specific environment variables
ENV SPRING_APPLICATION_NAME=scm \
    SPRING_DATASOURCE_URL=jdbc:mysql://db:3306/scm \
    SPRING_DATASOURCE_USERNAME=root \
    SPRING_DATASOURCE_PASSWORD=Divyansh123@ \
    CLOUDINARY_CLOUD_NAME=dpwypa0jw \
    CLOUDINARY_API_KEY=116524367389914 \
    CLOUDINARY_API_SECRET=OxGUhG0lTNc-jWjsY2NT7GibOnM \
    SPRING_MAIL_HOST=smtp.gmail.com \
    SPRING_MAIL_PORT=587 \
    SPRING_MAIL_USERNAME=emaileasetry@gmail.com \
    SPRING_MAIL_PASSWORD=ijipfhwotdehqwgb \
    GOOGLE_CLIENT_ID=313430345966-v3h8klmla70ktrp8motsv238i0rg907d.apps.googleusercontent.com \
    GOOGLE_CLIENT_SECRET=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 \
    GITHUB_CLIENT_ID=Iv23liZ19ZcfjlgLKa8g \
    GITHUB_CLIENT_SECRET=9aaae32528f3af2340cfcf8d91f7b520ec8457f5

# Add application JAR
COPY target/scm.jar /app/scm.jar

# Set working directory
WORKDIR /app

# Expose the default Spring Boot port
EXPOSE 8080

# Run the Spring Boot application
CMD ["java", "-jar", "scm.jar"]
