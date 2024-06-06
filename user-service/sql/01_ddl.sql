CREATE SCHEMA app
    AUTHORIZATION user_app;

CREATE TYPE user_role AS ENUM('ADMIN', 'USER', 'MANAGER');
CREATE TYPE user_status AS ENUM('WAITING_ACTIVATION', 'ACTIVATED', 'DEACTIVATED');
CREATE TYPE message_status AS ENUM('LOADED', 'OK', 'ERROR');

CREATE TABLE app.user
(
    uuid uuid,
    email character varying NOT NULL,
    fio character varying NOT NULL,
    role user_role NOT NULL,
    status user_status NOT NULL,
    password character varying NOT NULL,
    dt_create TIMESTAMP NOT NULL,
    dt_update TIMESTAMP NOT NULL,
    CONSTRAINT user_uuid_pk PRIMARY KEY (uuid),
    CONSTRAINT user_mail_unq UNIQUE (email)
);

CREATE TABLE app.message
(
    uuid uuid,
    verification_code varchar(6) NOT NULL,
    status message_status NOT NULL,
    dt_create TIMESTAMP NOT NULL,
    user_uuid uuid,
    CONSTRAINT message_uuid_pk PRIMARY KEY (uuid),
    CONSTRAINT message_user_uuid_fk FOREIGN KEY (user_uuid) REFERENCES app.user
);

ALTER TABLE IF EXISTS app.user
    OWNER to user_app;

ALTER TABLE IF EXISTS app.message
    OWNER to user_app;
