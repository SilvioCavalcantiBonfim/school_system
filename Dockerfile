FROM eclipse-temurin:17-jre-alpine
RUN mkdir app
ARG JAR_FILE
ADD ${JAR_FILE} /app/service.jar
WORKDIR /app
ENTRYPOINT java -jar service.jar