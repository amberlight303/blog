
--//////////////////////////////////////////////////// AUTH ////////////////////////////////////////////////////////////

------------------------------------------------------------------------------------------------------------------------
create table users_accounts (
    id serial primary key,
    first_name varchar(32),
    last_name varchar(32),
    email varchar(320),
    password varchar(64),
    enabled boolean,
    is_using_2fa boolean,
    unique (email)
);
------------------------------------------------------------------------------------------------------------------------
create table roles (
    id   serial primary key,
    name varchar(32)
);
------------------------------------------------------------------------------------------------------------------------
create table privileges (
    id   serial primary key,
    name varchar(32)
);
------------------------------------------------------------------------------------------------------------------------
create table users_locations (
    id   serial primary key,
    user_id int,
    enabled boolean,
    country varchar(32)
);
------------------------------------------------------------------------------------------------------------------------
create table devices_metadata (
    id   serial primary key,
    user_id int,
    device_details text,
    location text,
    last_logged_in timestamp
);
------------------------------------------------------------------------------------------------------------------------
create table passwords_reset_tokens (
    id   serial primary key,
    user_id int,
    token varchar(64),
    expiry_date timestamp
);
------------------------------------------------------------------------------------------------------------------------
create table verification_tokens (
    id   serial primary key,
    user_id int,
    token varchar(64),
    expiry_date timestamp
);
------------------------------------------------------------------------------------------------------------------------
create table new_location_tokens (
    id   serial primary key,
    user_location_id int,
    token varchar(64)
);
------------------------------------------------------------------------------------------------------------------------
create table users_roles (
    user_id int,
    role_id int,
    unique (user_id, role_id)
);
------------------------------------------------------------------------------------------------------------------------
create table roles_privileges (
    role_id int,
    privilege_id int,
    unique (role_id, privilege_id)
);
------------------------------------------------------------------------------------------------------------------------
insert into users_accounts (id, first_name, last_name, email, password, enabled, is_using_2fa)
values ( (select nextval('users_accounts_id_seq')), 'Nikola', 'Tesla', 'amberlight303@gmail.com',
         '$2a$11$0WE0P1I48qwdlencv7uloOdv8wiyU0OlhXhjbYPJjZnT1XaCB8klS', true, false);
------------------------------------------------------------------------------------------------------------------------
insert into roles (id, name)
values ( (select nextval('roles_id_seq')), 'ROLE_ADMIN');
------------------------------------------------------------------------------------------------------------------------
insert into privileges (id, name)
values ( (select nextval('privileges_id_seq')), 'READ_PRIVILEGE'),
       ( (select nextval('privileges_id_seq')), 'WRITE_PRIVILEGE');

------------------------------------------------------------------------------------------------------------------------
insert into users_roles (user_id, role_id)
values ( (select id from users_accounts where email = 'amberlight303@gmail.com'),
         (select id from roles where name = 'ROLE_ADMIN')
       );
------------------------------------------------------------------------------------------------------------------------
insert into roles_privileges (role_id, privilege_id)
values (
           (select id from roles where name = 'ROLE_ADMIN'),
           (select id from privileges where name = 'READ_PRIVILEGE')
       ),
       (
           (select id from roles where name = 'ROLE_ADMIN'),
           (select id from privileges where name = 'WRITE_PRIVILEGE')
       );
------------------------------------------------------------------------------------------------------------------------

--//////////////////////////////////////////////////// POSTS ///////////////////////////////////////////////////////////

------------------------------------------------------------------------------------------------------------------------
create table posts (
    id serial primary key,
    user_id int,
    title varchar(150),
    preview_text varchar(450),
    content text
);

insert into posts (id, user_id, title, preview_text, content)
values ((select nextval('posts_id_seq')), 1, 'Test title', 'Test preview text', 'Test content text');
------------------------------------------------------------------------------------------------------------------------
