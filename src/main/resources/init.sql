CREATE TABLE IF NOT EXISTS exercise.tbl_user
(
    email      varchar(100) not null primary key,
    id         bigint auto_increment unique,
    name       varchar(250),
    username   varchar(25)  not null,
    phone      integer,
    created_at timestamp    not null default current_timestamp(),
    updated_at timestamp    not null default current_timestamp()
);
