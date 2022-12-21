FROM gradle:7.5.0-jdk17 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build 

FROM openjdk:17-jdk-alpine
EXPOSE 8080
RUN mkdir /app
COPY --from=build /home/gradle/src/build/libs/*.jar /app/my-application.jar
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=dev","/app/my-application.jar"]





# FROM gradle:7.5.0-jdk17 AS build
# COPY --chown=gradle:gradle . /app

# FROM openjdk:17-jdk-alpine
# RUN mkdir /app
# COPY --from=build /home/gradle/src  /app