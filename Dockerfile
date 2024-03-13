# Use the official OpenJDK image as base
FROM openjdk:17-jdk-alpine

# Set the working directory in the container
WORKDIR /app

# Copy the packaged JAR file into the container
COPY target/KafkaClient-1.0-SNAPSHOT.jar /app/KafkaClient.jar

# Expose port 8080
EXPOSE 8080

# Command to run the Spring Boot application
CMD ["java", "-jar", "KafkaClient.jar"]
