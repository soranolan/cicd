#
# Build stage
#
FROM gradle:jdk15-hotspot AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src

ARG test_arg=$IS_STAGING

RUN echo "start"
RUN echo "test arg is"
RUN echo $test_arg
RUN echo "end"

RUN gradle build

#
# Package stage
#
FROM openjdk:15
RUN mkdir /app
COPY --from=build /home/gradle/src/build/libs/cicd-0.0.1-SNAPSHOT.jar /app/cicd-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java","-Dspring.profiles.active=prod","-jar","/app/cicd-0.0.1-SNAPSHOT.jar"]
