
FROM openjdk:17-jdk-slim


WORKDIR /app


COPY target/demo-0.0.1-SNAPSHOT.jar /app/receipt-processor.jar


EXPOSE 8080


ENTRYPOINT ["java", "-jar", "receipt-processor.jar"]
