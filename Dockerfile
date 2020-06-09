FROM mysql:latest

COPY /src/main/resources/db/ /docker-entrypoint-initdb.d
