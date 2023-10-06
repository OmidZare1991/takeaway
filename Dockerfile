# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-alpine

# Set the working directory in the container to / (root directory)
WORKDIR /

# Copy the compose.yaml file from the current directory to / in the container
COPY compose.yaml /compose.yaml

# Copy the application.properties file to /src/main/resources in the container
COPY application.properties /src/main/resources
COPY application-dev.properties /src/main/resources
COPY employee-service-management-1.0.0.jar /

# Specify the command to run your application
CMD ["java", "-jar", "employee-service-management-1.0.0.jar"]