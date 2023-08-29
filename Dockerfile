FROM openjdk:18
RUN apk update && apk add bash
VOLUME /tmp
EXPOSE 8080
ARG JAR_FILE=/workspaces/notesmaker/target/notemaker-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} notemaker.jar
ENTRYPOINT [ "java","-jar","notemaker.jar" ]