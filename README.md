# simple-news

# build

building app:
```
$ mvn clean install
```

next, you can optionally build docker image:
```
$ docker build -t simple-news .
```

# run


you can run app by executing:
```
$ export NEWSAPI_ORG_API_KEY=<api_key>
$ java -jar target/simple-news-1.0-SNAPSHOT.jar
```

or you can run your docker container:
```
$ docker run -p 8080:8080 -e "NEWSAPI_ORG_API_KEY=<api_key>" --name simple-news simple-news
```

### doc
rest api documentation is available under `localhost:8080/swagger-ui.html`

*This app is powered by newsapi.org*
