FROM gradle:7.4-jdk17 AS builder
WORKDIR /app
COPY build.gradle .
COPY settings.gradle .
COPY liberty-main-service/lombok.config .
COPY liberty-main-service liberty-main-service
COPY liberty-authentication-library liberty-authentication-library

RUN gradle :liberty-main-service:assemble

FROM openjdk:17-alpine
VOLUME /tmp
ARG LIBERTY_MAIN_JAR=/app/liberty-main-service/build/libs/*.jar
COPY --from=builder ${LIBERTY_MAIN_JAR} app.jar
EXPOSE 8080
ARG SPRING_PROFILES_ACTIVE=local
ENV SPRING_PROFILES_ACTIVE=$SPRING_PROFILES_ACTIVE
ENV TZ=Asia/Seoul
ENTRYPOINT ["java","-jar", "app.jar", "-Xmx2g", "-Xms256m", "-Dspring.profiles.active=${SPRING_PROFILES_ACTIVE}"]
