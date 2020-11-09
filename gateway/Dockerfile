FROM openjdk:11
MAINTAINER Oleg Koriachenko <amberlight303@gmail.com>

ADD ./target/gateway.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/gateway.jar"]

EXPOSE 9080