FROM amazoncorretto:21

WORKDIR /app

COPY build/libs/servicio-pagos-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]