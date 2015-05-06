## Build

With Maven:

```
mvn clean install
```

## Run

Everything at once with `docker-compose`:

```
docker-compose up -d
```

Or one by one:

 - `docker-compose up -d --no-deps registry`
 - `docker-compose up -d --no-deps dashboard`
 - `docker-compose up -d --no-deps config`
 - `docker-compose up -d --no-deps postgres`
 - `docker-compose up -d --no-deps customer`
 - `docker-compose up -d --no-deps mongo`
 - `docker-compose up -d --no-deps merchant`
 - `docker-compose up -d --no-deps payback`

### Notice:

When using `docker-compose` some services might not start up the first time if they happen to start
before the `config-service`. In this case just restart them individually as shown above.
