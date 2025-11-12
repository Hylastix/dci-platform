FROM icr.io/appcafe/open-liberty:full-java21-openj9-ubi-minimal

COPY --chown=1001:0 \
    src/main/liberty/config \
    /config/

COPY --chown=1001:0 \
    target/dci-platform.war \
    /config/apps

RUN mkdir /config/jdbc

RUN curl https://jdbc.postgresql.org/download/postgresql-42.7.7.jar -o /config/jdbc/postgresql-42.7.7.jar

RUN configure.sh