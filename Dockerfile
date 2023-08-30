FROM mysql:8.0.28
ENV MYSQL_ROOT_PASSWORD=nitai
ENV MYSQL_DATABASE=notemaker
EXPOSE 3306
CMD [ "mysqld","--version" ]
