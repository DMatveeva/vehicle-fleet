FROM postgis/postgis:latest
EXPOSE 5432
COPY ../initDB.sql /docker-entrypoint-initdb.d/
COPY ../populateDB.sql /docker-entrypoint-initdb.d/
COPY ../populateTracksDB.sql /docker-entrypoint-initdb.d/
ENV POSTGRES_USER postgres
ENV POSTGRES_PASSWORD user
ENV POSTGRES_DB fleet
CMD: [/bin/bash -c 'psql -U postgres -f /docker-entrypoint-initdb.d/initDB.sql && psql -U postgres -f /docker-entrypoint-initdb.d/populateDB.sql && psql -U postgres -f /docker-entrypoint-initdb.d/populateTracksDB.sql']
