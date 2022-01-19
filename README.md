# ABN Amro Assessment

[![CircleCI](https://circleci.com/gh/edisonfillus/abn-amro.svg?style=shield)](https://circleci.com/gh/edisonfillus/abn-amro)

[![Sonar](https://sonarcloud.io/api/project_badges/measure?project=abn-amro&metric=alert_status)](https://sonarcloud.io/summary/overall?id=abn-amro)
[![Sonar](https://sonarcloud.io/api/project_badges/measure?project=abn-amro&metric=sqale_rating)](https://sonarcloud.io/summary/overall?id=abn-amro)
[![Sonar](https://sonarcloud.io/api/project_badges/measure?project=abn-amro&metric=reliability_rating)](https://sonarcloud.io/summary/overall?id=abn-amro)
[![Sonar](https://sonarcloud.io/api/project_badges/measure?project=abn-amro&metric=security_rating)](https://sonarcloud.io/summary/overall?id=abn-amro)

[![Sonar](https://sonarcloud.io/api/project_badges/measure?project=abn-amro&metric=bugs)](https://sonarcloud.io/summary/overall?id=abn-amro)
[![Sonar](https://sonarcloud.io/api/project_badges/measure?project=abn-amro&metric=code_smells)](https://sonarcloud.io/summary/overall?id=abn-amro)
[![Sonar](https://sonarcloud.io/api/project_badges/measure?project=abn-amro&metric=coverage)](https://sonarcloud.io/summary/overall?id=abn-amro)
[![Sonar](https://sonarcloud.io/api/project_badges/measure?project=abn-amro&metric=duplicated_lines_density)](https://sonarcloud.io/summary/overall?id=abn-amro)
[![Sonar](https://sonarcloud.io/api/project_badges/measure?project=abn-amro&metric=ncloc)](https://sonarcloud.io/summary/overall?id=abn-amro)
[![Sonar](https://sonarcloud.io/api/project_badges/measure?project=abn-amro&metric=vulnerabilities)](https://sonarcloud.io/summary/overall?id=abn-amro)


### Objective
Create a web application which allows users to manage your favourite recipes.
Create API’s to show all available recipes and the actions to create, update and delete a recipe.
API’s could be able to retrieve recipes with following attributes:
1. Date and time of creation (formatted as dd‐MM‐yyyy HH:mm).
2. Indicator if the dish is vegetarian.
3. Indicator displaying the number of people the dish is suitable for.
4. Display ingredients as a list.
5. Cooking instructions.

### Requirements
   Please ensure that we have some documentation about the architectural choices and also how to
   run the application.
   The project is expected to be delivered as a GitHub repository URL or a zip file.
   All these requirements needs to be satisfied:
1. Application must be production ready.
2. REST application must be implemented using Java.
3. Data must be persisted in a database.
4. Use any frameworks of your choice for REST.
5. Unit testing must be taken in due consideration.
6. Describe at least 10 testing scenarios using GivenWhenThen style.
7. The API's must be built ensuring that it is secured from security attacks.

### Bonus
1. REST application should be secured by implementing authentication process (please provide
   credentials).
2. Application should have an API documentation.
3. Write automation tests for the described testing scenarios.
4. Use of container based solutions is an added advantage.
5. Creating a single-page application illustrating the use of API.

## Assumptions

### General
* No need to support multiple languages / locales
* No need to support multiple timezones, it will consider user inputted local time.
* No need to support idempotent transactions. Retrying to create a recipe will create a new one.
* No need to support concurrent updates control. All update transactions will override the recipe.

### Recipes
* No cross-validation if a vegetarian recipe contains any no vegetarian ingredients.
* The date and time of creation can't be updated, all other fields can.

### Ingredients
* Ingredients will be text plain, as there is no reference about keeping ingredients as separate entities.
* Ingredients should be persisted and recovered in the same order.
* Ingredients can be updated freely.

### Cooking Instructions
* Instructions will be text plain, without any relationship with other entities.
* Instructions should be persisted and recovered in the same order.
* Instructions can be updated freely.

## System Design / Architecture Decisions
* API is returning the date format as the requirements, but is strong advisable to return ISO8601 format in order to support internationalization.
* Ingredients and Cooking Instructions have its own tables. As there is only one editable string field, it was leveraged the JPA's `@ElementCollection` feature to avoid creating specific entities.
* It's using a MySQL as database. Just because it's the most popular one. Choose to use a relational just because it's a general application type of database.
  Note: In order to scale it should use a most suitable NoSQL database to query, like Redis.
* It's using Spring Data JPA. Just because it's more productive and eliminate a lot of SQL boilerplate to create an assessment. Using Lazy Loading in entity collections by default and using `@EntityGraph` in queries that need the full object to reduce database round trips. 
* It's not implementing idempotency in create method. In case of retrials, it will duplicate recipes with different identifiers.
  Suggestion: Use an idempotency that should be sent by the client, that should be used in retrials.
* It's not using a concurrent update control. In case of concurrent transactions, one will override the others changes.
  Suggestion: Use a versioning field on recipe to either implement an Optimistic Lock mechanism and use as version control. The client should always send the version when updating. In case of conflict, the api will return a 409 - Conflict.
* No auditing. There is no auditing tracking. As nothing mentioned in requirements about keeping user's and historical changes, not implementing it.
  Suggestion: Should have tracking of user changes, timestamps, IPs.
* Delete is physically eliminating all the information.
  Suggestion: Only inactivate the recipe, keeping track of all changes/auditing information.
* Using old school pagination. Using this form because it's supported by Spring Data and easy to implement. To scale it should be considered the use of cursors.
* Using MapStruct with `unmappedTargetPolicy=ERROR`, in order to throw a compilation error in any case that it identifies that it can't convert 1 to 1. This way we guarantee that all properties are corrected mapped, and we can afford to not unit test the mappers.
* Users are being store in the same database. Tokens JWT are being generated by the login endpoint. Just for simplicity.
  Suggestion: Use an OAUTH2 server like Keycloak.
* Using environment variables to store passwords, secrets, and other sensitive information. Just for simplicity.
  Suggestion: Use a solution like HashiCorp Vault.

## Security Measures
* All user input is validated using Java Validators to avoid any kind of injection.
* Insecure Direct Object Reference: Created a `RecipeRef` using UUID to not expose the internal ID.
* RegEx Denial of Service (ReDoS) attacks: First validate input type and size before testing regex.
* Passwords are being store on the database using BCrypt cryptography. 
* All API endpoints demands user authentication JWT tokens that are being issue using HMAC256.
* All API endpoints are secured using role-based security. Two roles exist: viewer and editor.
* To prevent Cross-Site Request Forgery (CSRF) attacks, enabled the Spring Security CSRF mechanism to generate unique CSRF tokens per user, and demanding the same token to be sent as X-XSRF-TOKEN http header. Swagger UI was configured to send it automatically.
* To prevent basic Denial of Service (DoS) attacks, created a rate limiter of 10 request/second for each IP address on Nginx Reverse Proxy. If you try to do more than 10 requests you will receive a HTTP 503 Service Temporarily Unavailable.
  ```webserver | 2022/01/19 15:37:42 [error] 26#26: *22 limiting requests, excess: 10.228 by zone "apiRateLimit", client: 192.168.128.1, server: nginx, request: "GET /api/v1/recipes?page=0&size=20 HTTP/1.1", host: "localhost", referrer: "http://localhost/swagger-ui/index.html"```
  Suggestion: This configuration is better suitable to be sited on the firewall. Using Nginx just for demonstration.

## Running the application
### Local mode
Execute the api as a standalone Spring application using an embedded H2 database:
```shell
cd assessment
./gradlew bootRun
```
Application can be accessed in http://localhost:8080/swagger-ui/index.html
### Container mode
Execute the api in containers, including a Nginx Reverse Proxy and MySQL database.
```shell
cd docker
docker-compose up
```
NOTE: First time it can take some minutes, as it will have to download all the images, dependencies and build the project.

When containers are up and running, application can be accessed in http://localhost/swagger-ui/index.html

Note: The MySQL is configured to just keep the data inside the container. So, if the container is destroyed the database will be newly fresh.

### API Documentation
Using the OpenAPI 3.0 generating the documentation from the code.
The package has the Swagger UI embedded, so you can see the docs and use the endpoints.

### Users
```javascript
username: 'viewer', password: '123456', roles: ['VIEWER'] // Can only list and query recipes
username: 'editor', password: '123456', roles: ['EDITOR'] // Can view, create, update and delete recipes
```
Use the /api/v1/login endpoint to login.

After getting the token, to use it in Swagger UI is need to click in the Authorize button on top of page. All configurations are done to send it automatically as a http header authorization.

### CI/CD Pipelines
Using CircleCI as CI/CD tool. Currently, includes steps for build, testing, code analysis and publishing results to SonarCloud.

Builds can be seen in here:
https://app.circleci.com/pipelines/github/edisonfillus/abn-amro

### Quality
Using SonarQube, publishing the results in SonarCloud.

Results can be seen in here:
https://sonarcloud.io/summary/overall?id=abn-amro




