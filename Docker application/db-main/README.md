# db
This repository will contain all the SQL migration scripts for the Todo application.
We are using flyway for performing the database migrations.
Dockerfile is used to create an image to perform database migrations using flyway by connecting to Postgresql database.
---

## Table of contents
  - [Prerequisites](#prerequisites)

## Prerequisites
* **docker** should be installed.
If you do not have node installed, kindly visit [docs.docker.com](https://docs.docker.com/get-docker/) to install it.
## Build image and Run Container
Run the following command in the terminal to build docker image.<br>
`docker image build -t info7205group5/db:latest .` <br><br>
Run the following command to start the executable container.<br>
`docker run --name flywayTodo --env dbhost="server" --env password="somepassword" --env dbport="5432" --env db="todo" --env username="postgres" --env schema="todo" info7205group5/db:latest`
