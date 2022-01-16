# ABN Amro Assessment

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

###Bonus
1. REST application should be secured by implementing authentication process (please provide
   credentials).
2. Application should have an API documentation.
3. Write automation tests for the described testing scenarios.
4. Use of container based solutions is an added advantage.
5. Creating a single-page application illustrating the use of API.

##Assumptions

###General
* No need to support multiple languages / locales
* No need to support multiple timezones, it will consider user inputted local time.
* No need to support idempotent transactions. Retrying a create recipe will create a new one.

###Recipes
* No cross-validation if a vegetarian recipe contains any no vegetarian ingredients.
* The date and time of creation can't be updated, all other fields can.

###Ingredients
* Ingredients will be text plain, as there is no reference about keeping ingredients as separate entities.
* Ingredients should be persisted and recovered in the same order.
* Ingredients can be updated freely.

###Cooking Instructions
* Instructions will be text plain, without any relationship with other entities.
* Instructions should be persisted and recovered in the same order.
* Instructions can be updated freely.

##System Design / Architecture Decisions
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

##Security Measures
* All user input is validated using Java Validators to avoid any kind of injection.
* Insecure Direct Object Reference: Created a `RecipeRef` using UUID to not expose the internal ID.
* RegEx Denial of Service (ReDoS) attacks: First validate input type and size before testing regex.

##Running the application
### Dev mode
```shell
./gradlew bootRun
```
