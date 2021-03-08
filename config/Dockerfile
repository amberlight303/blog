FROM openjdk:11
MAINTAINER Oleg Vostokov <amberlight303@gmail.com>

ADD ./target/config.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/config.jar"]

EXPOSE 9081