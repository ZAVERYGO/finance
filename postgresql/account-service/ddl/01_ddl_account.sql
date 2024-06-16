\c account

CREATE SCHEMA app
    AUTHORIZATION account_app;

CREATE TABLE app.account
(
    uuid uuid,
    title character varying NOT NULL,
    description character varying NOT NULL,
    balance INTEGER NOT NULL,
    type character varying NOT NULL,
    dt_create TIMESTAMP NOT NULL,
    dt_update TIMESTAMP NOT NULL,
    currency_uuid uuid NOT NULL,
    email character varying NOT NULL,
    CONSTRAINT account_uuid_pk PRIMARY KEY (uuid)
);

CREATE TABLE app.operation
(
    uuid uuid,
    date TIMESTAMP NOT NULL,
    description character varying NOT NULL,
    value INTEGER NOT NULL,
    dt_create TIMESTAMP NOT NULL,
    dt_update TIMESTAMP NOT NULL,
    currency_uuid uuid NOT NULL,
    category_uuid uuid NOT NULL,
    account_uuid uuid NOT NULL,
    CONSTRAINT operation_uuid_pk PRIMARY KEY (uuid),
    CONSTRAINT operation_account_uuid_fk FOREIGN KEY (account_uuid) REFERENCES app.account
);

ALTER TABLE IF EXISTS app.account
    OWNER to account_app;

ALTER TABLE IF EXISTS app.operation
    OWNER to account_app;
