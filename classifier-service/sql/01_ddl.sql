CREATE SCHEMA app
    AUTHORIZATION classifier_app;

CREATE TABLE app.currency
(
    uuid uuid,
    title VARCHAR(3) NOT NULL,
    description character varying,
    dt_create TIMESTAMP NOT NULL,
    dt_update TIMESTAMP NOT NULL,
    CONSTRAINT currency_uuid_pk PRIMARY KEY (uuid),
    CONSTRAINT currency_title_unq UNIQUE (title)
);


ALTER TABLE IF EXISTS app.currency
    OWNER to classifier_app;
