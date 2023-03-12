CREATE SCHEMA IF NOT EXISTS todo;

CREATE TABLE IF NOT EXISTS todo.Users
(
    firstName character varying(30) NOT NULL,
    lastName character varying(30) NOT NULL,
    middleName character varying(30),
    email character varying(50) NOT NULL UNIQUE,
    password character varying(30) NOT NULL,
    id SERIAL PRIMARY KEY
);


CREATE TABLE IF NOT EXISTS todo.Lists
(
    userId integer REFERENCES todo.Users(id) NOT NULL,
    name character varying(20) NOT NULL,
    created timestamp NOT NULL DEFAULT NOW(),
    lastModified timestamp NOT NULL DEFAULT NOW(),
    id SERIAL PRIMARY KEY
);


CREATE TYPE todo.priority AS ENUM ('High', 'Medium', 'Low');


CREATE TABLE IF NOT EXISTS todo.States
(
    id SERIAL PRIMARY KEY,
    state character varying(30) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS todo.Tasks
(
    listId integer REFERENCES todo.Lists(id) NOT NULL,
    summary character varying(50),
    task character varying(100) NOT NULL,
    dueDate timestamp NOT NULL,
    priority todo.priority,
    state integer REFERENCES todo.States(id) NOT NULL,
    created timestamp NOT NULL DEFAULT NOW(),
    lastModified timestamp NOT NULL DEFAULT NOW(),
    id SERIAL PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS todo.Attachments
(
    taskId integer REFERENCES todo.Tasks(id) NOT NULL,
    attachedDate timestamp NOT NULL DEFAULT NOW(),
    path character varying(200),
    name character varying(50),
    size integer,
    id SERIAL PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS todo.Comments
(
    taskId integer REFERENCES todo.Tasks(id) NOT NULL,
    comment character varying(150),
    created timestamp NOT NULL DEFAULT NOW(),
    lastModified timestamp NOT NULL DEFAULT NOW(),
    id SERIAL PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS todo.Reminders
(
    taskId integer REFERENCES todo.Tasks(id) NOT NULL,
    reminderDate timestamp NOT NULL,
    created timestamp NOT NULL DEFAULT NOW(),
    lastModified timestamp NOT NULL DEFAULT NOW(),
    id SERIAL PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS todo.Tags
(
    userId integer REFERENCES todo.Users(id) NOT NULL,
    name character varying(50),
    created timestamp NOT NULL DEFAULT NOW(),
    lastModified timestamp NOT NULL DEFAULT NOW(),
    id SERIAL PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS todo.TaskTags
(
    taskId integer REFERENCES todo.Tasks(id) NOT NULL,
    tagId integer REFERENCES todo.Tags(id) NOT NULL
   );

