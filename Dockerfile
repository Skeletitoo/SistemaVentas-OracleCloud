FROM openjdk:17-jdk-slim
WORKDIR /app
COPY . /app
CMD ["java", "-jar", "SistemaVentas/dist/SistemaVentas.jar"]
