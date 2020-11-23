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
sessions of users. Stateless implementation probably suits better for systems with an open registration. 
Sessions are stored in Redis. Abilities of users are divided by roles. Tokens, roles, users - all data is stored 
in PostgreSQL.

### Posts

Posts are stored in MongoDB. Full-text search is implemented using Elasticsearch, operations related to changing posts 
asynchronously reflect in Elasticsearch via Kafka. Ordinary posts retrieve operations are done using MongoDB.
Administrators can CRUD posts, users can only retrieve posts.

### Logging & Analytics

Log4j2 is used as a logging framework. 

### Features planned to implement further:
- Automatic deployment of external technologies via Docker
- Monitoring
- Pagination
- Search results highlighting
- Cache
- Hystrix integration





