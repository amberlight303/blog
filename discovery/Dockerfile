FROM openjdk:11
MAINTAINER Oleg Vostokov <amberlight303@gmail.com>

ADD ./target/discovery.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/discovery.jar"]

EXPOSE 9082