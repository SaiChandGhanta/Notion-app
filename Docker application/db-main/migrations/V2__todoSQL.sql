ALTER TABLE todo.Users ADD COLUMN "is_deleted" boolean DEFAULT FALSE;

ALTER TABLE todo.Tasks ADD COLUMN "is_deleted" boolean DEFAULT FALSE;

ALTER TABLE todo.Lists ADD COLUMN "is_deleted" boolean DEFAULT FALSE;

ALTER TABLE todo.Tags ADD COLUMN "is_deleted" boolean DEFAULT FALSE;

ALTER TABLE todo.TaskTags ADD COLUMN "is_deleted" boolean DEFAULT FALSE;

ALTER TABLE todo.Attachments ADD COLUMN "is_deleted" boolean DEFAULT FALSE;

ALTER TABLE todo.Comments ADD COLUMN "is_deleted" boolean DEFAULT FALSE;

ALTER TABLE todo.Reminders ADD COLUMN "is_deleted" boolean DEFAULT FALSE;