FROM openjdk:11-jdk
ARG JAR_FILE=*.jar
COPY build/libs/*.jar /application.jar
EXPOSE 8080/tcp
ENTRYPOINT ["java", "-jar", "/application.jar"]
