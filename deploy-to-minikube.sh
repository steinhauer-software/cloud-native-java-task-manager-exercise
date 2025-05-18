#!/bin/bash

# Ensure Minikube is running
minikube status || minikube start

# Build the Spring Boot app
./gradlew build -x test

# Build Docker image directly in Minikube's Docker daemon
eval $(minikube docker-env)
docker build -t task-manager:latest .

# Apply Kubernetes manifests
kubectl apply -f k8s/postgres.yaml
kubectl apply -f k8s/deployment.yaml
kubectl apply -f k8s/service.yaml

# Check the deployment status
echo "Waiting for deployment to complete..."
kubectl rollout status deployment/task-manager

# Setup port-forward
echo "Setting up port-forward to access the service"
echo "Service available at: http://localhost:8080"
echo "Press Ctrl+C to stop the port-forwarding when done"

# Run port-forward in foreground
kubectl port-forward service/task-manager 8080:8080
