FROM eclipse-temurin:21-ubi9-minimal
WORKDIR /app
ARG JAR_FILE=./target/*.jar
COPY ${JAR_FILE} /app/app.jar
EXPOSE 8080

ENTRYPOINT ["java","-jar","/app/app.jar"]

# Set an environment variable
ENV EUREKA_ENDPOINT="http://127.0.0.1:8761"