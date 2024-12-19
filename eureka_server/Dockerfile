FROM amazoncorretto:21
LABEL author="freze"
WORKDIR /app
COPY target/eureka_server-0.0.1-SNAPSHOT.jar /app/app.jar
ENTRYPOINT  ["java", "-jar", "app.jar"]