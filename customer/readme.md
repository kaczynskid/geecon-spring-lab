Build the project and the container:

```
mvn clean install docker:build
```

Run PostgreSQL database:

```
docker run --name geecon-postgres -e POSTGRES_PASSWORD=geecon -d postgres
```

Run [configuration service](../config/readme.md).

Run the app container and link the database and configuration containers:

```
docker run -d -P --name geecon-customer --link geecon-postgres:postgres --link geecon-config:config spring-lab-geecon/customer
```

To enable debug output add: `-e "DEBUG=true"`
