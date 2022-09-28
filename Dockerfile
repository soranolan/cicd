#FROM gradle:jdk15-hotspot AS build
FROM gradle:6.8.3-jdk15 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
#RUN gradle build -x test
RUN gradle clean build

FROM openjdk:15
RUN mkdir /app
COPY --from=build /home/gradle/src/build/libs/cicd-0.0.1-SNAPSHOT.jar /app/cicd-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java","-Dspring.profiles.active=prod","-jar","/app/cicd-0.0.1-SNAPSHOT.jar"]
