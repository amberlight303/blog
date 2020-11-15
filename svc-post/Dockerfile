FROM openjdk:11
MAINTAINER Oleg Koriachenko <amberlight303@gmail.com>

ADD ./target/svc-post.jar /app/
CMD ["java", "-Xmx500m", "-jar", "/app/svc-post.jar"]

EXPOSE 9083