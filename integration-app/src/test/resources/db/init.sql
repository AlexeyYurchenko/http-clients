CREATE SCHEMA IF NOT EXISTS app_schema;

create table app_schema.entities
(
    id   uuid not null primary key,
    date timestamp(6) with time zone,
    name varchar(255)
);

INSERT INTO entities(id, name.date)
values ('00e8cba9-05c2-4cd8-b08a-666a3a3cae00', 'testName_1', '2100-03-03 00:00:00.782169 +00:00');
INSERT INTO entities(id, name.date)
values ('00e8cba9-05c2-4cd8-b08a-666a3a3cae01', 'testName_2', '2100-03-03 00:00:00.782169 +00:00');
INSERT INTO entities(id, name.date)
values ('00e8cba9-05c2-4cd8-b08a-666a3a3cae02', 'testName_3', '2100-03-03 00:00:00.782169 +00:00');