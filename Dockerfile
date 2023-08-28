FROM maven:3.8.3-openjdk-17 AS build
COPY src /workspaces/notesmaker/src
COPY pom.xml /workspaces/notesmaker
RUN mvn -f /workspaces/notesmaker/pom.xml clean package
EXPOSE 8080
ENTRYPOINT ["java","-jar","/workspaces/notesmaker/target/notemaker-0.0.1-SNAPSHOT.jar"]