FROM openjdk:11-jdk
ARG JAR_FILE=*.jar
COPY build/libs/*.jar /application.jar
ENTRYPOINT ["java", "-jar", "/application.jar"]
