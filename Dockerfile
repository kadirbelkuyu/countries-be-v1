FROM openjdk:11-jre-slim

WORKDIR /app

ARG JAR_FILE=/target/*.jar

COPY asset /app/asset/
COPY ${JAR_FILE} /app/app.jar

ENV SPRING_PROFILES_ACTIVE=prod

CMD ["java", "-jar", "-Dspring.profiles.active=${SPRING_PROFILES_ACTIVE}", "app.jar"]



