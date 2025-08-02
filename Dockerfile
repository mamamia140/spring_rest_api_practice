FROM eclipse-temurin:21-jdk-jammy

WORKDIR /app

COPY target/*.jar app.jar

EXPOSE 8443

ENTRYPOINT ["java", "-jar", "app.jar"]
