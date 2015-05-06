Build the project and the container:

```
mvn clean install docker:build
```

Run the app container:

```
docker run -d -P --name geecon-registry spring-lab-geecon/registry
```

To enable debug output add: `-e "DEBUG=true"`
