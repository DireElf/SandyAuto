FROM openjdk:11-jdk-slim

WORKDIR /app

COPY .mvn/ .mvn

COPY mvnw pom.xml ./

RUN ./mvnw dependency:resolve

COPY src ./src

RUN chmod +x mvnw

RUN ./mvnw clean package

CMD ["./mvnw", "spring-boot:run"]
