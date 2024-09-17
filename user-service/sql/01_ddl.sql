CREATE SCHEMA app
    AUTHORIZATION user_app;


CREATE TABLE app.user
(
    uuid      uuid,
    email     character varying NOT NULL,
    fio       character varying NOT NULL,
    role      character varying NOT NULL,
    status    character varying NOT NULL,
    password  character varying NOT NULL,
    dt_create TIMESTAMP         NOT NULL,
    dt_update TIMESTAMP         NOT NULL,
    CONSTRAINT user_uuid_pk PRIMARY KEY (uuid),
    CONSTRAINT user_mail_unq UNIQUE (email)
);

ALTER TABLE IF EXISTS app.user
    OWNER to user_app;