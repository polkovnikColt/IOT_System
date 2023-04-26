#!/bin/sh
echo "shared_preload_libraries = 'pg_partman_bgw'" >> "$PGDATA"/postgresql.conf
echo "pg_partman_bgw.interval = 3600" >> "$PGDATA"/postgresql.conf
echo "pg_partman_bgw.role = '$POSTGRES_USER'" >> "$PGDATA"/postgresql.conf
echo "pg_partman_bgw.dbname = '$POSTGRES_DB'" >> "$PGDATA"/postgresql.conf

echo "shared_preload_libraries = 'pg_cron'" >> "$PGDATA"/postgresql.conf
echo "cron.database_name = '$POSTGRES_DB'" >> "$PGDATA"/postgresql.conf

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" -a -f /docker-entrypoint-initdb.d/schema.sql

pg_ctl restart
