# Docker Image for Postman Tests

This Docker image is designed to run Postman tests periodically using Newman, a command-line collection runner for Postman.

## Purpose

The purpose of this Docker image is to automate the execution of Postman tests against a specified endpoint at regular intervals. It can be useful for continuous testing, monitoring, or health checks of your API.

## How to Use

To use this Docker image, follow these steps:

1. **Build the Docker Image:** 
    ```shell
    docker build -f DockerFile -t postman-test-job .
    ```
2. **Run the Docker Container:**

    ```shell
    docker run -d --network="host" postman-test-job 
    ```
    


