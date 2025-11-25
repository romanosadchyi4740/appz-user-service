alter table users drop constraint if exists users_role_check;
alter table users add constraint users_role_check check ((role >= 0) and (role <= 3));

create table admin
(
    user_id bigint not null
        primary key
        constraint user_id_fkey
            references users
);
