create table role
(
    id       bigint primary key auto_increment not null,
    rolename varchar(255)
);

create table users
(
    id        bigint primary key auto_increment not null,
    email     varchar(255)                      not null,
    last_name varchar(255)                      not null,
    password  varchar(255)                      not null,
    username  varchar(255) unique               not null,
    age       bigint(100)                       not null
);

create table user_role
(
    user_id bigint not null,
    role_id bigint not null,

    primary key (user_id, role_id),
    foreign key (user_id) references user (id),
    foreign key (role_id) references role (id)
);