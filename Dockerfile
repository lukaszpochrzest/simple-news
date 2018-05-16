FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY target/simple-news-*.jar simple-news.jar
ENTRYPOINT ["java","-jar","/simple-news.jar"]