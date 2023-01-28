FROM postgis/postgis:latest
COPY ../initDB.sql /docker-entrypoint-initdb.d/
COPY ../populateDB.sql /docker-entrypoint-initdb.d/
COPY ../populateTracksDB.sql /docker-entrypoint-initdb.d/
ENV POSTGRES_USER postgres
ENV POSTGRES_PASSWORD user
ENV POSTGRES_DB fleet