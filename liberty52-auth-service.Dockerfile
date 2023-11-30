FROM gradle:7.4-jdk17 AS builder
WORKDIR /app
COPY build.gradle .
COPY settings.gradle .
COPY liberty52-auth-service liberty-auth-service
COPY liberty52-common liberty-common

RUN gradle liberty52-auth-service:assemble

FROM openjdk:17-alpine
VOLUME /tmp
ARG LIBERTY_AUTH_SERVICE_JAR=/app/liberty52-auth-service/build/libs/*.jar
COPY --from=builder ${LIBERTY_AUTH_SERVICE_JAR} app.jar
EXPOSE 8080
ARG SPRING_PROFILES_ACTIVE=local
ENV SPRING_PROFILES_ACTIVE=$SPRING_PROFILES_ACTIVE
ENV TZ=Asia/Seoul
ENTRYPOINT ["java","-jar", "app.jar","-Dspring.profiles.active=${SPRING_PROFILES_ACTIVE}"]