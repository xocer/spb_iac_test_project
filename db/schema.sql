create table directories
(
    id   serial primary key,
    date timestamp without time zone not null default now(),
    path varchar(200)
);

create table sub_directories
(
    id   serial primary key,
    path varchar(200),
    size varchar(200)
);
