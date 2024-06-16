\c audit

CREATE SCHEMA app AUTHORIZATION audit_app;

CREATE TABLE app.audit
(
    uuid uuid,
    dt_create timestamp,
    user_uuid uuid NOT NULL,
    text character varying NOT NULL,
    type character varying NOT NULL,
    id character varying NOT NULL,
    CONSTRAINT uuid_audit_pk PRIMARY KEY (uuid)
);

ALTER TABLE IF EXISTS app.audit
    OWNER to audit_app;

