FROM gradle:7.3.2-jdk11-alpine AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle assemble --no-daemon


FROM openjdk:11-jre-slim
COPY --from=build /home/gradle/src/build/libs/*.jar abn-amro-assessment-api.jar
EXPOSE 8080
CMD ["java", "-jar", "abn-amro-assessment-api.jar"]
