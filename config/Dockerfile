FROM java:8-alpine
VOLUME /config
ADD target/config.jar app.jar
RUN sh -c 'touch app.jar'
EXPOSE 8888
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Dspring.profiles.active=docker", "-jar","/app.jar"]