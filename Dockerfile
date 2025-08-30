FROM amazoncorretto:17-alpine-jdk
MAINTAINER KD
COPY target/flight-central-api.jar flight-central-api.jar
ENTRYPOINT ["java","-jar","/flight-central-api.jar"]