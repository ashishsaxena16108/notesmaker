FROM openjdk:17
ADD target/notemaker.jar notemaker.jar
ENTRYPOINT [ "java","-jar","/notemaker.jar" ]
