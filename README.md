[![Build Status](https://api.travis-ci.org/amberlight303/blog.svg?branch=master)](https://travis-ci.org/github/amberlight303/blog)
# Blog 
___

It's a complex java demo web app based on the microservices architecture. 
The core technologies are Spring Boot, Spring Cloud and Netflix OSS.

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

### Registration, authorization, authentication

The app features full-fledged registration, stateful A & A:
 - Password reset and account verification via e-mail
 - Geo IP location verification
 - 2FA
 - reCAPTCHA v3
 - Multiple login attempts control
 - "Remember me" service
 
Stateful A & A is done just for an example and study purpose because lots of enterprise apps require full control over 
sessions of users. Stateless implementation suits better for systems with an open registration.

Sessions are stored in Redis. Abilities of users are divided by roles. Tokens, roles, users - all data is stored 
in PostgreSQL. Registration and all auth features are placed in the gateway service.

### Posts

Posts are stored in MongoDB. Full-text search is implemented using Elasticsearch, operations related to changing posts 
asynchronously reflect in Elasticsearch via Kafka. Ordinary posts retrieve operations are done using MongoDB.
Administrators can CRUD posts, users can only retrieve posts.

### Struct module

The structures module is a lib that holds all structures, classes and util code that may be used by other modules.
The shared lib also makes easier implementations with serialization, deserialization and saving objects to different 
data bases.

### Logging, diagnostics, analytics

Log4j2 is used as a logging framework. There are two appenders: stdout and Kafka. Logs for Kafka are passed in a custom 
JSON format. Gateway and post services have their own Kafka topics for logging. Logstash consumes logs from Kafka and 
passes them to Elasticsearch. Kibana serves as a visualization dashboard for Elasticsearch.

Logging has two custom logging levels: BUSINESS (intLevel=5) and DIAG (intLevel=10). The system passes to Kafka logs 
that are below ERROR level of logging. It includes: BUSINESS, DIAG, FATAL, ERROR. Logs with level INFO and below are 
passed to stdout.

For each request the filter adds unique identifier (random UUID) as a header X-RID to each respective response. 
For tracking internal communication between modules, the system adds X-PTRID header that holds initial request ID 
from the first module.

Using ThreadContext provided by Log4j2 the system adds identifiers to each record: 
- UID (user ID)
- LRID (local request ID)
- PTRID (pass-trough request ID)
- IP (client's IP address)
- HOST (server name and port)

Custom JSON layout plugin uses CustomMessage class for logging. Besides String message, CustomMessage provides an 
opportunity to write logs with logEventId (Integer) and customFields (Map<String, Object>). Log event ID serves as a 
convenient anchor for fast logs aggregation and searching. For convenience, each app module can have a table for 
holding all log events identifiers and their descriptions. The log_events_svc_post table in PostgreSQL as an example. 

### Exceptions handling

The system has two custom basic exceptions: BusinessLogicException (expected case), ServerException (unexpected case). 
Each of them has log event ID and code fields. Exceptions handling is implemented with ControllerAdvice that intercepts 
all exceptions. The system by default responds with HTTP 450 for BusinessLogicException and with HTTP 500 for 
ServerException. A developer may extend these exceptions and add new handling methods for new exceptions. Exceptions 
handlers respond using JSON format.

### Features planned to implement further:
- Automatic deployment and initialization of all external technologies via Docker
- Monitoring
- Pagination
- Search results highlighting
- Cache
- Hystrix integration





