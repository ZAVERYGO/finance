FROM postgres:16.3-alpine3.19
MAINTAINER ITAcademy

ADD /user-service/ddl/00_init_user_db.sql /docker-entrypoint-initdb.d/
ADD /classifier-service/ddl/00_init_classifier_db.sql /docker-entrypoint-initdb.d/
ADD /account-service/ddl/00_init_account_db.sql /docker-entrypoint-initdb.d/
ADD /audit-service/ddl/00_init_audit_db.sql /docker-entrypoint-initdb.d/

ADD /user-service/ddl/01_ddl_user.sql /docker-entrypoint-initdb.d/
ADD /classifier-service/ddl/01_ddl_classifier.sql /docker-entrypoint-initdb.d/
ADD /account-service/ddl/01_ddl_account.sql /docker-entrypoint-initdb.d/
ADD /audit-service/ddl/01_ddl_audit.sql /docker-entrypoint-initdb.d/