create table projects
(
    id             varchar(255) not null
        primary key,
    date_of_create date,
    date_of_edit   date,
    description    varchar(255),
    title          varchar(255)
);

alter table projects
    owner to hits;

create table users
(
    id             varchar(255) not null
        primary key,
    date_of_create date,
    date_of_edit   date,
    login          varchar(255),
    name           varchar(255),
    password       varchar(255),
    role           varchar(255)
);

alter table users
    owner to hits;

create table comments
(
    id             varchar(255) not null
        primary key,
    date_of_create date,
    date_of_edit   date,
    text           varchar(255),
    author_id      varchar(255)
        constraint fkn2na60ukhs76ibtpt9burkm27
            references users
);

alter table comments
    owner to hits;

create table tasks
(
    id               varchar(255) not null
        primary key,
    date_of_creating date,
    date_of_edit     date,
    description      varchar(255),
    priority         varchar(255),
    project_id       varchar(255),
    time             time,
    title            varchar(255),
    creator_id       varchar(255)
        constraint fkt1ph5sat39g9lpa4g5kl46tbv
            references users,
    executor_id      varchar(255)
        constraint fkbrg922bkqn5m7212jsqjg6ioe
            references users
);

alter table tasks
    owner to hits;

create table tasks_comments
(
    task_id    varchar(255) not null
        constraint fklihyrcelwne0j48714tok4527
            references tasks,
    comment_id varchar(255) not null
        constraint fkqls9n8tb2w1rfb59xmd1k6x96
            references comments,
    primary key (task_id, comment_id)
);

alter table tasks_comments
    owner to hits;