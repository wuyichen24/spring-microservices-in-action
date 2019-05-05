# spring-microservices-in-action

This repository contains the source code of the book "[Spring Microservices in Action (John Carnell)](https://www.manning.com/books/spring-microservices-in-action)".

![](https://github.com/wuyichen24/spring-microservices-in-action/blob/master/readme/pics/Carnell-Spring-HI.png)

## Overview
This source code was re-organized by the [original source code](https://www.manning.com/downloads/1578) of the book and I make sure each module is runnable.

There are the differences between this source code and the original source code:
- Use Gradle as Java build automation tool rather than Maven.
- Use MySQL as database rather than PostgreSQL.
- Change the port of the organization service to 8060 to avoid the address conflict with the licensing service.
- Change the port of the special routes service to 8040 to avoid the address conflict with the licensing service.
- Change the port of the authentication service to 8901 to make it same with the example in the book.
- Add comments to make the code easy to read.

![](https://github.com/wuyichen24/spring-microservices-in-action/blob/master/readme/pics/whole_structure.png)

This microservices project is based on Spring Boot and Spring Cloud with [Netflix OSS](https://netflix.github.io/):
- Netflix Eureka (service discovery)
- Netflix Zuul (service gateway)
- Netflix Hystrix (resiliency patterns)
- Netflix Feign (HTTP API client)
- Netflix Ribbon (service communication, load balancing)

## Content List in Wiki
- [Overview](https://github.com/wuyichen24/spring-microservices-in-action/wiki)
- [Essentials](https://github.com/wuyichen24/spring-microservices-in-action/wiki/Essentials)
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

