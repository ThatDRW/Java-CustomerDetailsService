# CustomerDetailsService Application
## About
Small assignment in Java.
Running on Java 17 and Spring 2.

## Assignment and Progress
AssignmentOur business needs a solution for maintaining customer details and have asked you to develop a service that allows them to add, read, store and update customer details. 

- [x] The following details of a customer must be managed:
    - [x] First name
    - [x] Last name
    - [x] Age
    - [x] Current living address

- [x] The service must expose a REST API that enables the client to:
    - [x] Add a new customer
    - [x] Retrieve all customers
    - [x] Retrieve one customer by it's identifier
    - [x] Search customers by first name and/or last name
    - [x] Update the living address

- [ ] Technical requirements:
    - [x] Java 17 runtime
    - [x] Spring Boot 2/3
    - [ ] Cloud native microservice ready
    - [x] Production ready (unit test and code compliancy should be there)
    - [x] Persistence in a SQL DB (in memory DB will do for the assignment) (e.g. H2 in memory or dockerized Postgres)
    - [x] The project must be stored in GIT on either Github or Gitlab and must be made accessible (this is for understanding git related stuff)

- [x] Additionally:
    - [x] JWT Based Authentication
        - [x] Create User
        - [x] API Locked behind Bearer Token

    - [x] OpenAPI Documentation
        - [x] User Controller
        - [x] Customer Controller

## Instructions
Clone, then run with `mvn clean spring-boot:run`.

Import `com.thatdrw.customerdeailsservice.postman_collection.json` into Postman.

### Setup JWT Token
Run `1. Register User`. 

Run `4. Login User (w/ Correct Credentials)` and copy the `JWT Bearer Token` (_without 'Bearer '_) from the response header.

Paste this token in the `Authentication` tab, as type `Bearer Token` of the **com.thatdrw.customerdetailsservice COLLECTION**. The other Requests will inherit Authentication from the collection.

You can now run all `(Auth)` marked requests.

## h2 Console Access
The h2 console can be found at `localhost:8080/h2`

`JDBC URL     :   jdbc:h2:mem:customerdetailsservice`

`User Name    :   sa                                `

`Password     :   <blank>                           `

## OpenAPI Documentation Access
The API documentation can be found at: 
`localhost:8080/swagger-ui/index.html`

or at 
`localhost:8080/v3/api-docs` in raw Json.