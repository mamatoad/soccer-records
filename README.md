# Soccer records

Soccer records project is an information system, which helps its users to keep track of soccer match results. This system keeps track of soccer teams and their players. It supports entering information related to particular matches between two teams, such as list of goals for each match.

It is developed as a team project for PA165 Enterprise Applications in Java at FI MU.

## Modules:
soccer-records-api - API of service layer, also contains transfer objects.
soccer-records-logic - Business logic (implementation of persistence and service layer), uses JPA and Spring.
soccer-records-web - web presentation layer, uses action-based Stripes framework.

## Deployment:
To build the application, run "mvn clean install" from the project root directory.
To deploy it, prepare a JavaDB (Apache Derby) database, listening on port 1527 on localhost, database name pa165, username and password also pa165.
Then run "mvn tomcat7:run" from the soccer-records-web directory.
The web application will be accessible at http://localhost:8080/pa165
