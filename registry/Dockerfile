FROM java:8-alpine
VOLUME /registry
ADD target/registry-1.0.0-SNAPSHOT.jar app.jar
RUN sh -c 'touch app.jar'
EXPOSE 8761
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Dspring.profiles.active=docker", "-jar","/app.jar"]