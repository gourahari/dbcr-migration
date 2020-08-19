create table ApplicationDomain (
    ID number not null,
    CREATED timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
    DOMAIN_NAME varchar2(100),
    APP_NAME varchar2(100),
    ACTIVE number(1) DEFAULT 1 not null
);
/
CREATE SEQUENCE ApplicationDomain_Seq
 START WITH     1000
 INCREMENT BY   1
 NOCACHE
 NOCYCLE;
