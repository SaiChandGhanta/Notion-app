FROM flyway/flyway:latest-alpine

LABEL maintainer="ABC"

ENV dbhost="" 

ENV dbport="5432" 

ENV db="todo" 

ENV username="postgres" 

ENV password="" 

ENV schema="todo"

COPY /migrations/ /flyway/migrations/

ENTRYPOINT exec flyway migrate -url="jdbc:postgresql://${dbhost}:${dbport}/${db}" -defaultSchema=${schema} -user="${username}" -password="${password}" -locations="filesystem:/flyway/migrations"