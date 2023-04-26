FROM postgres:13.3-alpine

ENV PG_PARTMAN_VERSION v4.4.0
RUN apk update && apk upgrade && apk add --update alpine-sdk && \
    apk add --no-cache bash clang git llvm make cmake
# Install pg_partman
RUN set -ex \
    && wget -O pg_partman.tar.gz "https://github.com/pgpartman/pg_partman/archive/$PG_PARTMAN_VERSION.tar.gz" \
    && mkdir -p /usr/src/pg_partman \
    && tar \
        --extract \
        --file pg_partman.tar.gz \
        --directory /usr/src/pg_partman \
        --strip-components 1 \
    && rm pg_partman.tar.gz \
    && cd /usr/src/pg_partman \
    && make \
    && make install \
    && cd / \
    && rm -rf /usr/src/pg_partman

RUN git clone https://github.com/citusdata/pg_cron.git

RUN cd pg_cron && make && make install

COPY db/initdb.sh /docker-entrypoint-initdb.d
COPY db/schema.sql /docker-entrypoint-initdb.d

RUN sed -i -e 's/\r$//' /docker-entrypoint-initdb.d/initdb.sh

RUN chmod +x /docker-entrypoint-initdb.d/initdb.sh
RUN chmod +x /docker-entrypoint-initdb.d/schema.sql



