# F1 Betting Service

To build please use maven `mvn clean install`.

To run prerequisites use `docker compose up -d`. This will run the database required, mongodb 8.

To run the application use `java -jar F1-1.0-SNAPSHOT.jar`.

To run tests `mvn clean test`

# How to use

On startup the system will load all sessions and link drivers to the sessions. This will be updated every day via a cron job. 

The other operations are done via REST API which I have created a swagger UI page for an API definition, this can be loaded by the following link `http://localhost:8080/swagger-ui/index.html`
There is also `api.http` which are example api calls for the system
There are 3 endpoints:

* `POST /api/f1/bet` to place a bet
* `GET /api/f1/sessions` to get sessions
* `POST /api/f1/admin` to create an event as an admin

There is more information on parameters and expected outputs in the swagger. I did not implement the full CRUD operations due to time limitations.

# Future Work
Add more unit tests
Consider Event based system to have traceability of Bets and outcomes
Validation of User balance to be optimised
Add transactionality between placing bet DB operations
Add exception handling in controllers