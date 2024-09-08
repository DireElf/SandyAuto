FROM openjdk:11-jdk-slim

WORKDIR /app

COPY /app/.mvn/ .mvn

COPY /app/mvnw /app/pom.xml ./

RUN ./mvnw dependency:resolve

COPY /app/src ./src

RUN chmod +x mvnw

RUN ./mvnw clean package

CMD ["./mvnw", "spring-boot:run"]
