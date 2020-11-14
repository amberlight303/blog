FROM openjdk:11
MAINTAINER Oleg Koriachenko <amberlight303@gmail.com>

ADD ./target/config.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/config.jar"]

HEALTHCHECK --interval=30s --timeout=30s CMD curl -f http://127.0.0.1:9081/actuator/health || exit 1

EXPOSE 9081