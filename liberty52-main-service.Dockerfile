FROM gradle:7.4-jdk17 AS builder
WORKDIR /app
COPY build.gradle .
COPY settings.gradle .
COPY liberty52-main-service/lombok.config .
COPY liberty52-main-service liberty52-main-service
COPY liberty52-authentication-library liberty52-authentication-library
COPY liberty52-common liberty52-common

RUN gradle :liberty52-main-service:assemble

FROM openjdk:17-alpine
VOLUME /tmp
ARG LIBERTY_MAIN_JAR=/app/liberty52-main-service/build/libs/*.jar
COPY --from=builder ${LIBERTY_MAIN_JAR} app.jar
EXPOSE 8080
ARG SPRING_PROFILES_ACTIVE=local
ENV SPRING_PROFILES_ACTIVE=$SPRING_PROFILES_ACTIVE
ENV TZ=Asia/Seoul
ENTRYPOINT ["java","-jar", "app.jar", "-Xmx2g", "-Xms256m", "-Dspring.profiles.active=${SPRING_PROFILES_ACTIVE}"]
