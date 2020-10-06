DROP SCHEMA IF EXISTS EXERCISE;

CREATE SCHEMA IF NOT EXISTS EXERCISE;

DROP TABLE IF EXISTS EXERCISE;

CREATE TABLE IF NOT EXISTS EXERCISE.TBL_USER
(
    email      varchar(100) not null primary key,
    id         bigint auto_increment unique,
    name       varchar(250),
    username   varchar(25)  not null,
    phone      integer,
    created_at timestamp    not null default current_timestamp(),
    updated_at timestamp    not null default current_timestamp()
);
