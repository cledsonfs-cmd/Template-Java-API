CREATE SCHEMA IF NOT EXISTS template-java;

CREATE TABLE IF NOT EXISTS template-java.users (
    id integer NOT NULL,
    ativo boolean DEFAULT true,
    login character varying(255),
    password character varying(255),
    role smallint,
    CONSTRAINT users_role_check CHECK (((role >= 0) AND (role <= 2)))
);


INSERT INTO "template-java"."users"
(login, password, ativo, role)
SELECT 'adm@template.com', '$2a$10$sQafMsE0BBrMY6s5Wo1I7.3J0.50ZFSrw2kp7Cl68mjqyOVJcSSBm', true, 0
WHERE NOT EXISTS (SELECT 1 FROM "template-java"."users" WHERE login = 'adm@template.com');