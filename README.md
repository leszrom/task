# *Tasks*
An application for managing task list via the website. *Tasks* communicate with [Trello public API](https://developers.trello.com/reference#introduction). Provides connection with Trello Account and let you sent created tasks to chosen list on Trello board. *Tasks* send email after creating card in Trello and every day email with number of tasks in database.
*Tasks* is covered with unit test (JUnit & Mockito), and integration tests (MockMvc).\
`Java 8` `Gradle` `Spring Boot` `Hibernate` `CRUD` `REST` `Mail` `Thymeleaf` `Swagger` `Actuator` `Lombok` `JUnit` `Mockito` `MockMvc`

## Table of contents
* [Development guidelines](#development-guidelines)
* [Api Documentation](#api-documentation)
* [Database](#database)
* [Technologies](#technologies)
* [Deployment](#deployment)
* [Actuator](#actuator)
* [Source](#source)

## Development guidelines
* `./gradlew clean build` - build application (tests will be triggered)
* `./gradlew bootRun` - start application (available under localhost:8080)

## Api Documentation
Tasks has an API that meets the REST standard. The application accepts the following HTTP requests: GET, POST, PUT, DELETE.

[Swagger documentation](https://infinite-atoll-21692.herokuapp.com/swagger-ui.html#/)

![endpoints](https://user-images.githubusercontent.com/41355377/57110186-ef7baf00-6d37-11e9-9889-d96b868b98f1.PNG)

## Database
Tasks uses a relational MySQL database. The database stores a single table containing tasks. Each task has a title, description and a unique id.

## Technologies
Project is created with:
* Spring-boot version: 1.5.17.RELEASE
* spring-boot-starter-actuator
* spring-boot-starter-data-jpa
* boot:spring-boot-starter-mail version: 2.1.1.RELEASE
* spring-boot-starter-thymeleaf
* boot:spring-boot-starter-tomcat
* spring-boot-starter-web
* spring-boot-starter-test
* Swagger version: 2.92
* Lombok version: 1.18.4

**The application uses the following frameworks / libraries:**
1. Spring framework - allows you to reverse the control over the creation and management of objects and the injection of dependencies.
2. Hibernate framework - an ORM tool (relational-object mapping) implementing the JPA standard (unified API API). It allows you to map Java objects to Entities and vice versa.
3. Mysql connector java - a connector that allows you to connect to the MySQL database.
4. Lombok - a library allowing the use of annotations such as: @Getter, @Setter, @AllArgsConstructor, @NoArgsConstructor, which at the time of compilation automatically write characteristic fragments of the code, ie getters, setters or constructors.
5. Mockito - testing

## Deployment
**Back-end** is deployed on [Heroku](https://www.heroku.com/) (cloud application platform), and **Front-end** on [GitHubPages](https://pages.github.com/).
* Back-end: https://infinite-atoll-21692.herokuapp.com/
* Front-end: https://leszrom.github.io/

The Application deployed on Heroku, used PostgreSql instead of MySQL, because Heroku offers Postgres database for free.

## Actuator
**Spring Actuator** supply additional endpoints:
* [metrics](http://infinite-atoll-21692.herokuapp.com/metrics)
* [health](http://infinite-atoll-21692.herokuapp.com/health)
* [beans](http://infinite-atoll-21692.herokuapp.com/beans)
* [mappings](http://infinite-atoll-21692.herokuapp.com/mappings)
* [info](http://infinite-atoll-21692.herokuapp.com/info)

## Source
This app was created and developed during 'Java Developer' programming course ([Kodilla.com](https://kodilla.com/pl)).
Frontend for this app is based on source code given by Kodilla.
