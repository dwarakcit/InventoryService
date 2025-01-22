# STEP 1: Use the official OpenJDK base image
FROM openjdk:21-jdk-slim

# STEP 2: Set the working directory inside the container
WORKDIR /app

# STEP 3: Copy the built JAR file into the container
COPY build/libs/InventoryService-0.0.1-SNAPSHOT.jar app.jar

# STEP 4: Set the entry point for the application
ENTRYPOINT ["java", "-jar", "app.jar"]