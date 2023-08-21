# Demonstrating database connectivity locally and on Azure

Demonstrating connectivity between a simple Java Spring Boot application and PostgreSQL database run locally and on Azure with ([Postgresql Flexible Server](https://learn.microsoft.com/en-us/azure/postgresql/flexible-server/quickstart-create-server-portal)). We will use separate application[-env].properties files to change our configuration (decide which database to use). The database in Azure will demo as the production datasase (all env-variables/credentials are masked).

Key word here is __SIMPLE__. No Rest API, data mangement, persistense, data models, Hibernate etc. Only testing connectivity across multiple environments without exposing any secrets.

This project is used for teaching purposes only.

## Requirements

* [Docker Desktop](https://docs.docker.com/desktop/install/windows-install/) and [Docker Hub account](https://hub.docker.com/)
* Java
* Maven
* [Azure account](https://azure.microsoft.com/en-us/free/) and [subscription](https://learn.microsoft.com/en-us/dynamics-nav/how-to--sign-up-for-a-microsoft-azure-subscription)

## Local setup

Create the following environment variable:
* ENVIRONMENT=`dev` (for local development, will make Spring load `application-dev.properties`)

__HEADS UP!__ Ignore the next step if you have a local instance of PostgreSQL running. Make sure to update the properties in [application-dev.properties](https://github.com/rezabmirzaei/db-demo/blob/main/src/main/resources/application-dev.properties) accordingly. 
Open a terminal and run:
* `docker run --name localpostgresdb -p 5432:5432 -e POSTGRES_PASSWORD=postgres -d postgres`

This will set up a local Postgres database `postgres`, with access given by user `postgres`. For the sake of simplicity, we've given it the passwod `postgres`. If you change any of this, remember to update `application-dev.properties` accordingly.

Open a terminal and from the root folder of the project, run:

* `.\mvnw spring-boot:run`

If done correctly, this runs the application and you will see it connect to the local database:

![Success!](images/local_connection_success.png?raw=true "Local connection success!")

#### Docker

Create an image of the application and push it to your Docker Hub account (used later for deployment to Azure). Open a terminal and from the root folder of the project, run:

* `docker build -t <YOUR_DOCKER_USERNAME>/db-demo .`
* `docker push <YOUR_DOCKER_USERNAME>/db-demo`

## PostgreSQL on Azure with access from local machine

This part assumes you have an active [Azure account](https://portal.azure.com/) and a valid [subscription](https://learn.microsoft.com/en-us/dynamics-nav/how-to--sign-up-for-a-microsoft-azure-subscription).

Log into your Azure account and create an [Azure Database for PostgreSQL - Flexible Server](https://learn.microsoft.com/en-us/azure/postgresql/flexible-server/quickstart-create-server-portal) as described in the linked documentation. During creation, under the `Networking` tab, make sure to:

* Check `Allow public access from any Azure service within Azure to this server` (neccessary later for our app service)
* Add your current client IP to the firewall (so you can connect to it from you local environment)

![Azure DB Networking](images/azure_db_networking.png?raw=true "Azure DB Networking")

Create the following environment values (locally):

* POSTGRES_URL=`jdbc:postgresql://<URL TO YOUR POSTGRES DB ON AZURE>/<DATABASENAME>`
* POSTGRES_USER=`<USERNAME YOU SUPPLIED WHILE CREATING THE DB>`
* POSTGRES_PASSWORD=`<PASSWORD YOU SUPPLIED WHILE CREATING THE DB>`

URL to your database can be found on the `Overview` and the `Connection strings` panes. DATABASENAME is by default `postgres`.

To test connection to this database, first change your ENVIRONMENT variable to:
* ENVIRONMENT=`prod` (this will make Spring load `application-prod.properties`)

`application-prod.properties` points to the above environment variables and the values of these should __NEVER__ be openly distributed (e.g. checked into git).

Open a __NEW__ terminal to reset your environment and load the new variables and values, and from the root folder of the project run:

* `.\mvnw spring-boot:run`

If done correctly, this should run the application and you should see it connect to the database on __Azure__:

![Success!](images/azure_connection_success.png?raw=true "Azure connection success!")

## Azure App Service

In Azure (same as above), create an [App Service](https://learn.microsoft.com/en-us/azure/app-service/) on Linux for Docker Container.

During setup, when you come to the "Docker" stage, point to your Docker Hub repository and Docker image (this). See [this video for a good demo](https://www.youtube.com/watch?v=_LNOg8kU4CE).

After the appservice is created, navigate to it and in the menu, select _Configuration_ > _Application Settings_. Add the environment variables from above as new application settings and save. This will automatically restart your App Service and the changes will take effect.

Check the applicaiton logs by going to the App Service, in the menu selecting _App Service logs_ or _Log stream_.

Friendly pointer: Choose **West Europe** as region if cost is an issue. Often the cheapest alternative. See [pricing calculator](https://azure.microsoft.com/en-us/pricing/details/app-service/linux/).

### NOTE

If you wanna test the demo application container and postgres db container locally, without running docker-compose, you have to set up some Docker networking, e.g.:

* docker network create db-demo-network
* docker network connect db-demo-network db-demo
* docker network connect db-demo-network localpostgresdb







