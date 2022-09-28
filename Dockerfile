FROM gradle:jdk15-hotspot AS build

RUN echo $IS_STAGING
ARG test_arg=$IS_STAGING
RUN echo $test_arg

COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build

FROM openjdk:15
RUN mkdir /app
COPY --from=build /home/gradle/src/build/libs/cicd-0.0.1-SNAPSHOT.jar /app/cicd-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java","-Dspring.profiles.active=prod","-jar","/app/cicd-0.0.1-SNAPSHOT.jar"]
