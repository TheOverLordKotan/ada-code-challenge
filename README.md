# Challenge Event Sourcing in Microservices

This repository is a microservice reference example that is intended to teach the basics of event sourcing in Spring Boot applications for a chanllenge code.

## System Architecture

For this reference, I chose to create a simple example domain with a high degree of relationships between data stored on separate microservices. In the architecture diagram below, you'll see an abstract component diagram that describes an event-driven microservice architecture containing three domain services and Orchestration-based saga .

The application consists of two services:

* `checkout-service` - creates orders and Orchestration-based saga to triggered bill-service to sum orders and logistic-service create a sent orderer
* `bill-service` - manages bill sum products order´s
* `logistic-service` - manages logistic-service
 

Both services are implemented using Spring Boot, JPA and the AXON framework 

The `checkout-service` uses a saga to enforce the bill-service to sum the ordens and the logistic-service .

== About sagas

http://microservices.io/patterns/data/saga.html[Sagas] are a mechanism for maintaining data consistency in a http://microservices.io/patterns/microservices.html[microservice architecture].
A saga is a sequence of transactions, each of which is local to a service.

There are two main ways to coordinate sagas: orchestration and choreography.
This example uses orchestration-based sagas, where a saga (orchestration) object invokes the participants.

A saga orchestrator is a persistent object that does one of two things:

1. On startup, it sends a command message to a participant
2. When it receives a reply, it updates its state and sends a command message to the next participant.

To learn more about why you need sagas if you are using microservices:

* the http://microservices.io/patterns/data/saga.html[Saga pattern]
* my http://microservices.io/microservices/general/2018/03/22/microxchg-sagas.html[microXchg 2018 presentation]
* read about sagas in my https://www.manning.com/books/microservice-patterns[Microservice patterns book]


## Conventions

One of the main problems I see today when describing components of a microservice architecture is a general ambiguity in the roles of separate services. For this reason, this example will describe a set of conventions for the roles of separate services.

### Domain Services

**Domain services** are microservices that own the _system of record_ for a portion of the application's domain.

![Domain service](https://imgur.com/Lgy55OJ.png)

Domain services:

- Manage the storage of domain data that it owns.
- Produce the API contract for the domain data that it owns.
- Produce events when the state of any domain data changes.
- Maintain relationship integrity to domain data owned by other services.

### Aggregate Services

**Aggregate services** are microservices that replicate eventually consistent views of domain data owned by separate _domain services_.

![Aggregate service](https://imgur.com/1jx6rTn.png)

Aggregate services:

- Subscribe to domain events emitted by separate domain services.
- Maintain an ordered immutable event log for events it receives.
- Create connected query projections of distributed domain data.
- Provide performant read-access to complex views of domain data.

=== Architecture

The following diagram shows the architecture of the Architecture overview of a CQRS based Axon application.

image::./images/axon_Architecture.png[]

The application consists of three services:

* `checkout-service` - implements the REST endpoints for managing customers.
The service persists the `Orders` JPA entity in a H2 database.
Using the https://docs.axoniq.io/reference-guide/architecture-overview/ddd-cqrs-concepts#view-models-or-projections[Axon Saga framework], it processes command messages, updates its the `order` entity, and sends back a reply messages.

* `bill-service` - implements a REST endpoint for managing orders.
The service persists the `bill` JPA entity  in H2 database.
Using the https://docs.axoniq.io/reference-guide/architecture-overview/ddd-cqrs-concepts#view-models-or-projections[Axon Saga framework], it sends command messages and processes replies to bill and invoice products.

* `logistic-service` - implements a REST endpoint for managing orders.
The service persists the `shipping` JPA entity in H2 database.
Using the https://docs.axoniq.io/reference-guide/architecture-overview/ddd-cqrs-concepts#view-models-or-projections[Axon Saga framework], it sends command messages and processes replies to logistic  products.


== Building and running

Note: you do not need to install Gradle since it will be downloaded automatically.
You just need to have Java 8 installed.

First, build the application

```
./mvn install 
./mvn spring-boot:run
```

Next, launch the services using https://docs.docker.com/compose/[Docker Compose]:
Open the terminal and start the docker
```
./systemctl start docker
```
Starting Containers
This is the most easiest step in this post. Simply run the below command in the directory that holds your docker-compose.yml to start both containers.
```
./ cd docker/
./docker-compose up
```
You can verify that the Docker images were created by using below command.
```
./docker image ls
```
You can verify that the Docker containers were created by using below command.

Note:

If the containers aren't accessible via `localhost` - e.g. you are using Docker Toolbox, you will have to use `${DOCKER_HOST_IP}` instead of localhost.

== Using the application

Once the application has started, you can use the application via the Swagger UI:

* `Order Service` - `http://localhost:8081/swagger-ui.html`
* `Customer Service` - `http://localhost:8089/swagger-ui.html`
* `logistic Service` - `http://localhost:8082/swagger-ui.html`

You can also use `curl` to interact with the services.
First, let's create a customer:
 create an order:

```bash
$ curl -X POST --header "Content-Type: application/json" -d '{
  "clientId":"108",
  "date":"2012-01-30T00:00:00",
  "direction":"av cra 8 56-29",
  "currency":"COP",
  "products":[
    {
        "productId":"LAPTOP",
        "productQuantity":"100",
        "productCost":"1"
    },    {
        "productId":"LAPTOP",
        "productQuantity":"200",
        "productCost":"1"
    }
  ]
  
}' http://127.0.0.1:8089/api/orders

HTTP/1.1 200
Content-Type: application/json;charset=UTF-8

54ecdb5f-4028-4bbf-a427-229c820bf073

```
== Got questions?

Don't hesitate to create an issue or see


