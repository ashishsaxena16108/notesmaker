FROM openjdk:18
WORKDIR /workspaces/notesmaker/src
COPY . /workspaces/notesmaker/src
CMD [ "java", "/workspaces/notesmaker/target/notemaker-0.0.1-SNAPSHOT.jar"]