FROM openjdk:18
WORKDIR /notesmaker/src
COPY . /notesmaker/src
CMD [ "java", "-jar","notemaker.jar"]