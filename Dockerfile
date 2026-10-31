FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY . /app
CMD ["java", "-jar", "SistemaVentas/dist/SistemaVentas.jar"]
