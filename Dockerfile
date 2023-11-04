# Specifying that we are using the Maven image with OpenJDK 17 for the build stage and giving it an alias "build"
FROM maven:3.8.4-openjdk-17 AS build

# Setting the working directory inside the container for the build stage
WORKDIR /app

# Copying all files from the current directory (on the host machine) to the working directory of the container
COPY . .

# Executing the project build using Maven
RUN mvn clean install

# Specifying that for the run stage, we will use Alpine image with OpenJDK 17
FROM openjdk:17-alpine

# Installing curl and cleaning up to keep the image small
RUN apk --no-cache add curl

# Setting the working directory for the run stage
WORKDIR /app

# Copying the JAR file from the build stage ("build") to the working directory of the current stage
COPY --from=build /app/target/training-microservice.jar .

# Setting the command to run the JAR file
CMD ["java", "-jar", "training-microservice.jar"]
