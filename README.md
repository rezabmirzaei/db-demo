# Demonstrating database connectivity locally and on Azure

Demonstrating connectivity between a simple Java Spring Boot application and PostgreSQL database locally and on Azure ([Postgresql Flexible Server](https://learn.microsoft.com/en-us/azure/postgresql/flexible-server/quickstart-create-server-portal)), configured with separate application[-env].properties. The database in Azure will demo as the production datasase.

Key word here is __SIMPLE__. No Rest API, data mangement, persistense, data models, Hibernate etc... Kept as simple as possible:

__CAN THE JAVA APPLICATION CONNECT TO THE DATABASE ACROSS VARIOUS ENVIRONMENTS?__

## Requirements

* Docker Desktop and Docker Hub account
* Java
* Maven
* Azure account

## Setup

### Environment

Create the following environment variable:
* ENVIRONMENT=``dev`` (for local development, will make Spring load ``application-dev.properties``)

(more environment variables will be needed soon, after we create database in Azure)

### PostreSQL database

#### Local setup

Open a terminal and run (remember to set ``YOUR PASSWORD`` to the same as the environment variable above!):
* ``docker run --name localpostgresdb -p 5432:5432 -e POSTGRES_PASSWORD=<YOUR PASSWORD> -d postgres``

This will set up your local Postgres database called ``postgres``, with access given by user ``postgres`` and the password you supplied in the command. For the sake of simplicity, give it the passwod ``postgres``.

Open a terminal and from the root folder of the project, run:

* ``.\mvnw spring-boot:run``

If done correctly, this should run the application and you should see it connect to the database:

![Success!](images/local_connection_success.png?raw=true "Local connection success!")

## Setup on Azure

Log into your Azure account and create an [Azure Database for PostgreSQL - Flexible Server](https://learn.microsoft.com/en-us/azure/postgresql/flexible-server/quickstart-create-server-portal) as described in the linked documentation. During creation, under the ``Networking`` tab, make sure to:

* Check ``Allow public access from any Azure service within Azure to this server`` (neccessary later for our app service)
* Add your current client IP to the firewall

![Azure DB Networking](images/azure_db_networking.png?raw=true "Azure DB Networking")

Create the following environment values (locally):

* POSTGRES_URL=``jdbc:postgresql://<URL TO YOUR POSTGRES DB ON AZURE>/<DATABASENAME>``
* POSTGRES_USER=``<USERNAME YOU SUPPLIED WHILE CREATING THE DB>``
* POSTGRES_PASSWORD=``<PASSWORD YOU SUPPLIED WHILE CREATING THE DB>``

URL to your database can be found on the ``Overview`` and the ``Connection strings`` panes. DATABASENAME is by default ``postgres``.

To test connection to this database, first change your ENVIRONMENT variable to:
* ENVIRONMENT=``prod`` (this will make Spring load ``application-prod.properties``)

Open a __NEW__ terminal to reset your environment and load the new variables and values and from the root folder of the project, run:

* ``.\mvnw spring-boot:run``

If done correctly, this should run the application and you should see it connect to the database on __Azure__:

![Success!](images/azure_connection_success.png?raw=true "Azure connection success!")

### App Service

[TODO]

### NOTE

If you wanna test the demo application container and postgres db container locally, without running docker-compose, you have to set up some Docker networking, e.g.:

* docker network create db-demo-network
* docker network connect db-demo-network db-demo
* docker network connect db-demo-network localpostgresdb







