-- drop states table
ALTER TABLE todo.Tasks DROP CONSTRAINT tasks_state_fkey;
ALTER TABLE todo.Tasks DROP COLUMN state;
DROP TABLE todo.States; 
ALTER TABLE todo.Tasks ADD state character varying(30);

-- rename column id to user_id in Users
ALTER TABLE todo.Users RENAME COLUMN id TO user_id;

-- alter columns
ALTER TABLE todo.Users ALTER COLUMN password TYPE character varying(100);
ALTER TABLE todo.Lists RENAME COLUMN userId TO user_id;

-- email verification
CREATE TABLE IF NOT EXISTS todo.Tokens
(
    token character varying(100) UNIQUE NOT NULL,
    token_expiry_time timestamp,
    email character varying(50) UNIQUE
);

ALTER TABLE todo.Users ADD is_verified boolean DEFAULT FALSE;

--adding id primary key to TaskTags
ALTER TABLE todo.TaskTags ADD id SERIAL PRIMARY KEY;

-- modifying task to description in Tasks
ALTER TABLE todo.Tasks RENAME COLUMN task TO description;
ALTER TABLE todo.Tasks ALTER COLUMN description DROP NOT NULL;
ALTER TABLE todo.Tasks ALTER COLUMN dueDate DROP NOT NULL;
ALTER TABLE todo.Tasks ALTER COLUMN description TYPE character varying(250);

--dropping priority type
DROP TYPE IF EXISTS todo.priority CASCADE;
ALTER TABLE todo.Tasks ADD priority character varying(20);