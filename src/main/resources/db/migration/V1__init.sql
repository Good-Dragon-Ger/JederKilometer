create table if not exists activity (
    id serial primary key,
    uuid uuid,
    sport uuid,
    km double precision,
    date date,
    name uuid
);

create table if not exists users (
    id serial primary key,
    uuid uuid,
    username varchar(255),
    name varchar(255),
    team uuid
);

create table if not exists teams (
    id serial primary key,
    uuid uuid,
    name varchar(255),
    years int
);

create table if not exists sport (
    id serial primary key,
    uuid uuid,
    sport varchar(255),
    price double precision,
    category varchar(255),
    active boolean
);

create table if not exists hash_data (
    id serial primary key,
    hash int,
    inserted boolean
);