[![Build Status](https://api.travis-ci.org/amberlight303/blog.svg?branch=master)](https://travis-ci.org/github/amberlight303/blog)
# Blog 
___

It's a complex java demo web app based on the microservices architecture. 
The core technologies are Spring Boot, Spring Cloud and Netflix OSS. The system is fully RESTful, no front-end so far.

### App core modules

- Config service
- Discovery service
- Gateway service
- Post service
- Structures

### Technologies in use

- Redis
- PostgreSQL
- MongoDB
- Kafka
- Elasticsearch
- Logstash
- Kibana
- Travis CI
- Docker

### Registration, authorization and authentication

The app features full-fledged registration, stateful A & A:
 - Password reset and account verification via e-mail
 - Geo IP location verification
 - 2FA
 - reCAPTCHA v3
 - Multiple login attempts control
 - "Remember me" service

Stateful A & A is done just for an example and study purpose because lots of enterprise apps require full control over 
sessions of users. Stateless implementation suits better for systems with an open registration.

Log in is bound to `/login` and takes `form-data` request body format. Sessions are stored in Redis. 
Abilities of users are divided by roles. Tokens, roles, users - all data is stored in PostgreSQL. Registration and 
all auth features are placed in the gateway service.

### Posts

Posts are stored in MongoDB. Full-text search is implemented using Elasticsearch, operations related to changing posts 
asynchronously reflect in Elasticsearch via Kafka. Ordinary posts retrieval operations are done using MongoDB.
Administrators can CRUD posts, users can only retrieve posts. Validation is made using annotations. The persistence 
layer is done using Spring Data `JpaRepository` (for PostgreSQL) and `CrudRepository` (for MongoDB) that provide a 
domain specific languages for making requests to data bases.

### Struct module

The structures module is a lib that holds all structures, classes and util code that may be used by other modules.
The shared lib also makes easier implementations with serialization, deserialization and saving objects to different 
data bases.

### Logging, diagnostics and analytics

Log4j2 is used as a logging framework. There are two appenders: stdout and Kafka. Logs for Kafka are passed in a custom 
JSON format. Gateway and post services have their own Kafka topics for logging. Logstash consumes logs from Kafka and 
passes them to Elasticsearch. Kibana serves as a visualization dashboard for Elasticsearch.

Logging has two custom logging levels: BUSINESS (intLevel=5) and DIAG (intLevel=10). The system passes to Kafka logs 
that are below ERROR level of logging. It includes: BUSINESS, DIAG, FATAL, ERROR. Logs with level INFO and below are 
passed to stdout.

For each request the filter adds a unique identifier (random UUID) as a header `X-RID` to each respective response. 
For tracking internal communication between modules, the system adds `X-PTRID` header that holds initial request ID 
from the first module.

Using ThreadContext provided by Log4j2 the system adds identifiers to each record: 
- *`UID`* (user ID)
- *`LRID`* (local request ID)
- *`PTRID`* (pass-trough request ID)
- *`IP`* (client's IP address)
- *`HOST`* (server name and port)

Custom JSON layout plugin uses CustomMessage class for logging. Besides String message, CustomMessage provides an 
opportunity to write logs with logEventId (`Integer`) and customFields (`Map<String, Object>`). Log event ID serves as a 
convenient anchor for fast logs aggregation and searching. For convenience, each app module can have a table for 
holding all log events identifiers and their descriptions. The log_events_svc_post table in PostgreSQL as an example. 

### Exceptions handling

The system has two custom basic exceptions: BusinessLogicException (expected case), ServerException (unexpected case). 
Each of them has log event ID and code fields. Exceptions handling is implemented with ControllerAdvice that intercepts 
all exceptions. 

The system by default responds with HTTP 450 for BusinessLogicException and with HTTP 500 for 
ServerException. A developer can extend these exceptions and add new handling methods for new exceptions. Exceptions 
handlers respond using JSON format.

### Features planned to implement further

- Automatic deployment and initialization of all external technologies via Docker
- Monitoring
- Pagination
- Search results highlighting
- Cache
- Hystrix integration

### Deployment

Before deployment of the system, define and export environment variables listed in 
[.env](https://github.com/amberlight303/blog/blob/master/.env).
Few containers are partially set to local use for now. The system requires a few gigs of space in RAM and the disk.

Make sure these ports are free on your machine:
```
9080 - Gateway
9081 - Config
9082 - Discovery
9083 - Posts service
2181 - Zookeper for Kafka
9092 - Kafka
9200 - Elasticsearch
9600 - Logstash
5601 - Kibana
6379 - Redis
5432 - PostgreSQL
27017 - MongoDB
```

After environment variables are exported and after external are technologies installed, configured and initialized, run:
```
docker-compose -f docker-compose.yml up -d
```
When the system starts, it takes requests on default port `9080`. Predefined user with the role `ADMIN` has 
username `someTestUser@gmail.com` and password `password`.

### A few screenshots: 

<p align="center">
    <img src="https://user-images.githubusercontent.com/26651009/100383274-e8a54300-3025-11eb-80bf-bdbce0755be6.png"/>
</p>

<p align="center">
    <img src="https://user-images.githubusercontent.com/26651009/100383284-ef33ba80-3025-11eb-894c-452e3d89fa67.png"/>
</p>

<p align="center">
    <img src="https://user-images.githubusercontent.com/26651009/100383291-f0fd7e00-3025-11eb-9199-ce503ff692cc.png"/>
</p>

<p align="center">
    <img src="https://user-images.githubusercontent.com/26651009/100383297-f4910500-3025-11eb-939f-65b6e14f889f.png"/>
</p>

<p align="center">
    <img src="https://user-images.githubusercontent.com/26651009/100386400-e5ae5080-302d-11eb-8e9a-ee03430ddc8f.png"/>
</p>
