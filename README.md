# Tesing database connectivity locally and on Azure

[WORK IN PROGRESS - not complete yet]

Test basic setup with a simple Java Spring Boot application and PostgreSQL database locally and on Azure with App Service and Postgresql Flexible Server.

Key word here is __BASIC__. No Rest API, data mangement, persistense, data models, Hibernate etc... I've kept it as simple as I possibly can. I only want to test the following:

__CAN THE JAVA APPLICATION CONNECT TO THE DATABASE?__

## Requirements

* Docker
* Java
* Maven
* Azure account

## Local setup

### Environment

Create the following environment variables
* ENVIRONMENT=``dev``
* POSTGRES_PASSWORD=``<YOUR PASSWORD>``

### PostreSQL database

Open a terminal and run
* ``docker run --name localpostgresdb -p 5432:5432 -e POSTGRES_PASSWORD=<YOUR PASSWORD> -d postgres``

## Setup on Azure

### App Service

### Postgresql Flexible Server







