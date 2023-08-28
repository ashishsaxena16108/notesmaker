FROM openjdk:18
WORKDIR /workspaces/notesmaker/src
COPY . /workspaces/notesmaker/src
CMD [ "java", "-jar","notemaker.jar"]