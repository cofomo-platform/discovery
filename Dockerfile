FROM openjdk:13-jdk-alpine

ARG version
ENV version=$version

COPY target/discovery-$version.jar /usr/spring/discovery.jar

WORKDIR /usr/spring

RUN sh -c 'touch discovery.jar'

ENTRYPOINT java -jar -Dspring.profiles.active=prod discovery.jar