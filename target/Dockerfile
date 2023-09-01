FROM openjdk:17-jdk-alpine

RUN mkdir /notes

COPY target/*.jar /notes/notemaker.jar

ENTRYPOINT ["java","-jar","/notes/notemaker.jar"]