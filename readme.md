 \## About
Small assignment in Java.

## Instructions
Clone, then run with `mvn clean spring-boot:run`.

Import `com.thatdrw.customerdeailsservice.postman_collection.json` into Postman.

Run `1. Register User` and copy the `JWT Bearer Token` (_without 'Bearer '_)from the response header.

Paste this token in the `Authentication` tab, as type `Bearer Token` of the **com.thatdrw.customerdetailsservice COLLECTION**. The other Requests will inherit Authentication from the collection.

You can now run all `(Auth)` marked requests.

## h2 Console Access
The h2 console can be found at `localhost:8080/h2`

`JDBC URL     :   jdbc:h2:mem:customerdetailsservice`

`User Name    :   sa                                `

`Password     :   <blank>                           `

## OpenAPI Documentation Access
The API documentation can be found at `localhost:8080/swagger-ui/index.html`

or at `localhost:8080/v3/api-docs` in raw Json.