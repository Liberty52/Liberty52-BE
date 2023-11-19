FROM gradle:7.4-jdk17 AS builder
WORKDIR /app
COPY build.gradle .
COPY settings.gradle .
COPY src src
COPY liberty-main/lombok.config .
COPY liberty-authentication-library liberty-authentication-library

RUN gradle :liberty-authentication-library:assemble

RUN gradle assemble

FROM openjdk:17-alpine
VOLUME /tmp
ARG JAR_FILE=/app/build/libs/*.jar
COPY --from=builder ${JAR_FILE} app.jar
EXPOSE 8080
ARG SPRING_PROFILES_ACTIVE=dev
ENV SPRING_PROFILES_ACTIVE=$SPRING_PROFILES_ACTIVE
ENV TZ=Asia/Seoul
ENTRYPOINT ["java","-jar", "app.jar", "-Xmx2g", "-Xms256m", "-Dspring.profiles.active=${SPRING_PROFILES_ACTIVE}"]
