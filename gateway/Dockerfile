FROM openjdk:11
MAINTAINER Oleg Vostokov <amberlight303@gmail.com>

ADD ./target/gateway.jar /app/
CMD ["java", "-Xmx500m", "-jar", "/app/gateway.jar"]

EXPOSE 9080