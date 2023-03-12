# todo-docker-compose
This repo contains the docker compose file to deploy the various services (example db, flyway and app etc) of the todo application.
<br></br>

We expect the following environment variables to be set before deploying the services
 - POSTGRES_PASSWORD
 - POSTGRES_USER
 - POSTGRES_DB

To pass environment variables to a compose file, please refer this [Environment variables in Compose](https://docs.docker.com/compose/environment-variables/)

## Passing environment variables using .env files
- Clone the repo
- Add a new file and name it **.env** in the cloned repo folder
- The contents to enter into the **.env** file are shown in the **.env.example**

## Deploying using Docker Compose
`docker-compose up`
