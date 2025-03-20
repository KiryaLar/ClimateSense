create table sensor
(
    id   serial primary key,
    name varchar
);


create table measurement
(
    id          serial primary key,
    sensor_id   int references sensor (id) on delete cascade,
    temperature numeric(2, 4) not null,
    raining     bool          not null
);