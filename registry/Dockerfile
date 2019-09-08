FROM java:8-jre
MAINTAINER Jimmy Luo <jimmy.zw.luo@gmail.com>

ADD ./target/registry.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/registry.jar"]

EXPOSE 8761