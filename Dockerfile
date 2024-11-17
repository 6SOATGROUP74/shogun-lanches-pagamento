FROM openjdk:17-alpine

MAINTAINER 2024_6SOAT

COPY ./target/shogun-lanches-pagamento-1.jar app.jar

ENV SPRING_PROFILES_ACTIVE=prod

ENTRYPOINT ["java", "-jar", "/app.jar"]

EXPOSE 8080