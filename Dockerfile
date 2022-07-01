FROM mysql:8
ADD ./db/init.sql /docker-entrypoint-initdb.d/
RUN chmod +x /docker-entrypoint-initdb.d/init.sql

FROM openjdk:11-jdk
ARG JAR_FILE=*.jar
COPY build/libs/*.jar /application.jar
RUN gradle clean bootJar
ENTRYPOINT ["java", "-jar", "/application.jar"]