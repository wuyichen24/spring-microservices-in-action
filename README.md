# spring-microservices-in-action

[![Build Status](https://travis-ci.org/wuyichen24/spring-microservices-in-action.svg?branch=master)](https://travis-ci.org/wuyichen24/spring-microservices-in-action)
[![License](https://img.shields.io/badge/License-Apache%202.0-green.svg)](https://opensource.org/licenses/Apache-2.0) 

This repository contains the source code of the book "[Spring Microservices in Action (John Carnell)](https://www.manning.com/books/spring-microservices-in-action)" and the personal summary of technical essentials about Spring Boot for microservices.

![](https://github.com/wuyichen24/spring-microservices-in-action/blob/master/readme/pics/Carnell-Spring-HI.png)

## Overview
This source code was re-organized by the [original source code](https://www.manning.com/downloads/1578) of the book and I make sure each module is runnable.

There are the differences between this source code and the original source code:
- Use Gradle as Java build automation tool rather than Maven.
- Use MySQL as database rather than PostgreSQL.
- Change the port of the organization service to 8060 to avoid the address conflict with the licensing service.
- Change the port of the special routes service to 8040 to avoid the address conflict with the licensing service.
- Change the port of the authentication service to 8901 to make it same with the example in the book.
- Add the integration of Splunk for log aggregation locally (use both local Splunk and remote Papertrail).
- Use the original functionality in Logback for sending the log to the log servers (Splunk & Papertrail) rather than the solution in the Book (use Logspout to direct the docker output to the log servers).
- Replace the user_roles table by the authorities table for storing users' credentials to DB.
- Change the column name from user_name to username for the user_orgs table and the users table.
- Add comments to make the code easy to read.

![](https://github.com/wuyichen24/spring-microservices-in-action/blob/master/readme/pics/whole_structure/whole_structure.png)

This microservices project is based on 
- **Core**
   - Spring Cloud with [Netflix OSS](https://netflix.github.io/)
      - [Netflix Eureka (service discovery)](https://github.com/Netflix/eureka)
      - [Netflix Zuul (service gateway)](https://github.com/Netflix/zuul)
      - [Netflix Hystrix (resiliency patterns)](https://github.com/Netflix/hystrix)
      - [Netflix Feign (HTTP API client)](https://github.com/OpenFeign/feign)
      - [Netflix Ribbon (service communication, load balancing)](https://github.com/Netflix/ribbon)
- **Message Queue**
   - [Apache Kafka](https://kafka.apache.org/) with [Apache ZooKeeper](https://zookeeper.apache.org/)
- **Cache**
   - [Redis](https://redis.io/)
- **Database**
   - [MySQL](https://www.mysql.com/)
- **Security**
   - [OAuth2](https://oauth.net/2/)
- **Log**
   - [Splunk](https://www.splunk.com/)
   - [Papertrail](https://papertrailapp.com/)
- **Trace**
   - [Apache Zipkin](https://zipkin.apache.org/)

## Content List in Wiki
- [Overview](https://github.com/wuyichen24/spring-microservices-in-action/wiki)
- [Getting Started](https://github.com/wuyichen24/spring-microservices-in-action/wiki/Getting-Started)
   - [ZooKeeper](https://github.com/wuyichen24/spring-microservices-in-action/wiki/Getting-Started#install-apache-zookeeper)
   - [Kafka](https://github.com/wuyichen24/spring-microservices-in-action/wiki/Getting-Started#install-apache-kafka)
   - [Redis](https://github.com/wuyichen24/spring-microservices-in-action/wiki/Getting-Started#install-redis)
   - [Splunk](https://github.com/wuyichen24/spring-microservices-in-action/wiki/Splunk)
- [Technical Essentials](https://github.com/wuyichen24/spring-microservices-in-action/wiki/Technical-Essentials)
   - [Autowired](https://github.com/wuyichen24/spring-microservices-in-action/wiki/Autowired)
   - [SpringData JPA](https://github.com/wuyichen24/spring-microservices-in-action/wiki/SpringData-JPA)
   - [Configuration File Auto-loading](https://github.com/wuyichen24/spring-microservices-in-action/wiki/Configuration-File-Auto-loading)
   - [Configuration Encryption](https://github.com/wuyichen24/spring-microservices-in-action/wiki/Configuration-Encryption)
   - [Service Discovery with Eureka](https://github.com/wuyichen24/spring-microservices-in-action/wiki/Service-Discovery-with-Eureka)
   - [Resiliency Patterns with Hystrix](https://github.com/wuyichen24/spring-microservices-in-action/wiki/Resiliency-Patterns-with-Hystrix)
   - [Configure Hystrix](https://github.com/wuyichen24/spring-microservices-in-action/wiki/Configure-Hystrix)
   - [Service Gateway with Zuul](https://github.com/wuyichen24/spring-microservices-in-action/wiki/Service-Gateway-with-Zuul)
   - [Zuul Filters](https://github.com/wuyichen24/spring-microservices-in-action/wiki/Zuul-Filters)
   - [Protect Service with Spring Security and OAuth2](https://github.com/wuyichen24/spring-microservices-in-action/wiki/Protect-Service-with-Spring-Security-and-OAuth2)
   - [Use JWT as Access Token](https://github.com/wuyichen24/spring-microservices-in-action/wiki/Use-JWT-as-Access-Token)
   - [Store Clients and Users' Credentials to DB](https://github.com/wuyichen24/spring-microservices-in-action/wiki/Store-Clients-and-Users'-Credentials-to-DB)
   - [Integrate with Message Queue (Kafka)](https://github.com/wuyichen24/spring-microservices-in-action/wiki/Integrate-with-Message-Queue-(Kafka))
   - [Integrate with Redis](https://github.com/wuyichen24/spring-microservices-in-action/wiki/Integrate-with-Redis)
   - [Tune Logging](https://github.com/wuyichen24/spring-microservices-in-action/wiki/Tune-Logging)
   - [Log Aggregation](https://github.com/wuyichen24/spring-microservices-in-action/wiki/Log-Aggregation)
   - [Send Trace to Zipkin](https://github.com/wuyichen24/spring-microservices-in-action/wiki/Send-Trace-to-Zipkin)
   - [Build Runnable Jar](https://github.com/wuyichen24/spring-microservices-in-action/wiki/Build-Runnable-Jar)
- [Core Application Logic](https://github.com/wuyichen24/spring-microservices-in-action/wiki/Core-Application-Logic)
- [Components](https://github.com/wuyichen24/spring-microservices-in-action/wiki/Components)
   - [Servers]()
      - [Config](https://github.com/wuyichen24/spring-microservices-in-action/wiki/Config-Server)
      - [Eureka](https://github.com/wuyichen24/spring-microservices-in-action/wiki/Eureka-Server)
      - [Zipkin](https://github.com/wuyichen24/spring-microservices-in-action/wiki/Zipkin-Server)
      - [Zuul](https://github.com/wuyichen24/spring-microservices-in-action/wiki/Zuul-Server)
   - [Services]()
      - [Licensing](https://github.com/wuyichen24/spring-microservices-in-action/wiki/Licensing-Service)
      - [Organization](https://github.com/wuyichen24/spring-microservices-in-action/wiki/Organization-Service)
      - [Authentication](https://github.com/wuyichen24/spring-microservices-in-action/wiki/Authentication-Service)
      - [Special Routes](https://github.com/wuyichen24/spring-microservices-in-action/wiki/Special-Routes-Service)
   - [Database](https://github.com/wuyichen24/spring-microservices-in-action/wiki/Database)
   - [Message Queue](https://github.com/wuyichen24/spring-microservices-in-action/wiki/Message-Queue)
   - [Cache](https://github.com/wuyichen24/spring-microservices-in-action/wiki/Cache)
   - [Log Server]()
      - [Splunk](https://github.com/wuyichen24/spring-microservices-in-action/wiki/Splunk-Server)
