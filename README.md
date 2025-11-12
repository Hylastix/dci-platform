# DCI-PLATFORM

This is the backend platform for DCI. It provides an API for creating and retrieving scores, and updating measurements.
Note: The deployment does not support deploying the frontend currently.

## Requirements

- podman and podman-compose have to be installed
- podman user socket has to be enabled
- Java JDK 21 has to be installed

## Configuration

- Add file called .server.env to root directory with the necessary information to connect to the database

```toml
db.host=db
db.port=5432
db.name=dci
db.user=dci
db.password=<password>
```

- Add file called .db.env to root directory with the necessary information to configure the database itself

```toml
POSTGRES_USER=dci
POSTGRES_DB=dci
POSTGRES_PASSWORD=<password>
```

Note: User management is currently not implemented. For development purposes, a basic registry with preconfigured
users and roles is provided in the source code. A key store for OpenLiberty has to be provided.

## How to run

1. Compile the web application:

```bash
mvn package
```

2. Create podman network if it doesn't already exist:

```bash
podman network create --ignore dci
```

3. Start container:

```bash
podman-compose build
podman-compose up -d
```
