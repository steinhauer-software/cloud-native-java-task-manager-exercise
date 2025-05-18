@echo off

REM Ensure Minikube is running
minikube status || minikube start

REM Build the Spring Boot app
call gradlew build -x test

REM Build Docker image directly in Minikube's Docker daemon
FOR /F "tokens=*" %%i IN ('minikube docker-env --shell cmd') DO %%i
docker build -t task-manager:latest .

REM Apply Kubernetes manifests
kubectl apply -f k8s\postgres.yaml
kubectl apply -f k8s\deployment.yaml
kubectl apply -f k8s\service.yaml

REM Check the deployment status
echo Waiting for deployment to complete...
kubectl rollout status deployment/task-manager

REM Setup port-forward
echo Setting up port-forward to access the service
echo Service available at: http://localhost:8080
echo Press Ctrl+C to stop the port-forwarding when done

REM Run port-forward in foreground
kubectl port-forward service/task-manager 8080:8080
