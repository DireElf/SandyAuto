FROM openjdk:11-jdk-slim

WORKDIR /app

COPY . /app

RUN chmod +x mvnw

RUN ./mvnw clean package

CMD ["./mvnw", "spring-boot:run"]
