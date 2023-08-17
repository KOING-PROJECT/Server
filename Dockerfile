FROM openjdk:11-jdk
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
RUN mkdir /app && \
    unzip app.jar -d /app
ENTRYPOINT ["java", "-Dspring.profiles.active=local", "-jar", "app.jar"]