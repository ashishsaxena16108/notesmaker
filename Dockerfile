FROM mysql:8.0.28 AS helper
ENV MYSQL_ROOT_PASSWORD=nitai
ENV MYSQL_DATABASE=notemaker
EXPOSE 3306
CMD [ "mysqld","--version" ]

FROM openjdk:17-jdk-alpine
RUN mkdir /app
COPY target/*.jar /notemaker/notemaker.jar
ENTRYPOINT ["java","-jar","/notemaker/notemaker.jar"]