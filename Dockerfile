# Specifying that we are using the Maven image with OpenJDK 17 for the build stage and giving it an alias "build"
FROM maven:3.8.4-openjdk-17 AS build

# Setting the working directory inside the container for the build stage
WORKDIR /app

# Copying all files from the current directory (on the host machine) to the working directory of the container
COPY . .

# Executing the project build using Maven
RUN mvn clean install

# Specifying that for the run stage, we will use the lightweight OpenJDK 17 JDK image
FROM openjdk:17-jdk-slim

# Setting the working directory for the run stage
WORKDIR /app

# Installing curl in the container
RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*

# Copying the JAR file from the build stage ("build") to the working directory of the current stage
COPY --from=build /app/target/training-microservice.jar .

# Setting the command to run the JAR file
CMD ["java", "-jar", "training-microservice.jar"]
