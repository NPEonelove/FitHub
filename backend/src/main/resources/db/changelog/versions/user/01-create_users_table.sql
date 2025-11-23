create table users
(
    user_id           uuid primary key                                 not null,
    email             varchar(128) check ( length(email) >= 5 ) unique not null,
    password          varchar(512) check ( length(password) >= 6 )     not null,
    role              varchar(8) check ( role in ('USER', 'ADMIN') ) default 'USER',
    registration_date timestamp                                      default current_timestamp
)