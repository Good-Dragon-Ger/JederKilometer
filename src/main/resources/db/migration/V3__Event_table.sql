create table if not exists event_time (
    id serial primary key,
    uuid uuid,
    start_date date,
    end_date date,
    event_prices uuid
);

create table if not exists event_price (
    id serial primary key,
    category varchar(255),
    price double precision,
    event_time uuid
)