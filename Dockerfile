FROM gradle:8.13-jdk21 AS builder
WORKDIR /app
COPY . .
RUN gradle --no-daemon build -x test

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar

# Expose port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
