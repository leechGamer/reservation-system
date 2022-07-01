FROM mysql:8
ADD ./db/init.sql /docker-entrypoint-initdb.d/
RUN chmod +x /docker-entrypoint-initdb.d/init.sql
