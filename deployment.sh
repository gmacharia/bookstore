#!/bin/bash

# Define your Docker Hub credentials
DOCKER_USERNAME="gmasharia"
DOCKER_PASSWORD="dckr_pat_tGlsVL34ch7gTf4z4G1wJ-gtdrA"

# Function to login to Docker Hub
login_to_dockerhub() {
    echo "Logging in to Docker Hub..."
    echo "$DOCKER_PASSWORD" | docker login -u "$DOCKER_USERNAME" --password-stdin
}

# Function to pull Docker images
pull_docker_images() {
    echo "Pulling Docker images..."
    docker pull gmasharia/service-registry
    docker pull gmasharia/config-server
    docker pull gmasharia/spring-gateway
    docker pull gmasharia/book-service
    docker pull gmasharia/customer-service
    docker pull gmasharia/order-service
    docker pull mysql:5.7
}

# Function to run Docker containers
run_docker_containers() {
    echo "Running Docker containers..."
    # Example of running containers
    #docker run -d --name serviceregistry -p 8761:8761  gmasharia/service-registry
    #docker run -d --name configserver -p 8088:8088  gmasharia/config-server
    #docker run -d --name springgateway -p 8090:8090 gmasharia/spring-gateway
    docker run -d --name mysql-container -p3307:3306 -e MYSQL_ROOT_PASSWORD='RbQ|9raBA3BYd]N)tpEYJ31B5<19' biarms/mysql:5.7
    docker run -d --name bookservice -p 8281:8281 gmasharia/book-service
    #docker run -d --name customerservice -p 8283:8283 gmasharia/customer-service
    #docker run -d --name orderservice -p 8282:8282 gmasharia/order-service
    #docker run -d --name openzipkin/zipkin-slim -p 3307:3306 mysql-container
}

# Main function
main() {
    login_to_dockerhub
    pull_docker_images
    run_docker_containers
}

# Execute the main function
main

