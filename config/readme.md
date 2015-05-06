Build the project and the container:

```
mvn clean install docker:build
```

Run the app container:

```
docker run -d -P --name geecon-config spring-lab-geecon/config
```

To enable debug output add: `-e "DEBUG=true"`

To change configuration repository URL: `-e "SPRING_CLOUD_CONFIG_SERVER_GIT_URI=file:///repo"`

To mount local directory to `/repo` in container: `-v /home/darek/Devel/Workspace/Home/geecon/spring-lab-config:/repo`
