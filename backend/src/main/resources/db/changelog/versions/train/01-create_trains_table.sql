create table trains
(
    train_id    uuid primary key     default gen_random_uuid(),
    user_id     uuid        references users (user_id) on delete set null,
    title       varchar(32) not null,
    description varchar(2048),
    created_at  timestamp   not null default current_timestamp
);

create table exercises
(
    exercise_id uuid primary key     default gen_random_uuid(),
    train_id    uuid references trains (train_id) on DELETE cascade,
    title       varchar(32) not null,
    text        varchar(1024),
    created_at  timestamp   not null default current_timestamp
);