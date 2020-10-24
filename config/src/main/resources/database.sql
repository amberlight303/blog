
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
insert into users_accounts (id, first_name, last_name, email, password, enabled, is_using_2fa)
values ( (select nextval('users_accounts_id_seq')), 'Nikola', 'Tesla', 'amberlight303@gmail.com',
         '$2y$11$enDqQYULgbxAWzCrjZ.j7Owl9OAHNpkXZIZJHEyI51ZGB1BCWjQNi', true, false);
------------------------------------------------------------------------------------------------------------------------
insert into roles (id, name)
values ( (select nextval('roles_id_seq')), 'ROLE_ADMIN');

------------------------------------------------------------------------------------------------------------------------
insert into users_roles (user_id, role_id)
values ( (select id from users_accounts where email = 'amberlight303@gmail.com'),
         (select id from roles where name = 'ROLE_ADMIN')
       );
------------------------------------------------------------------------------------------------------------------------

--//////////////////////////////////////////////////// ETC ///////////////////////////////////////////////////////////
------------------------------------------------------------------------------------------------------------------------
create table log_events_svc_post (
    id integer primary key,
    description text,
    date_created timestamp default current_timestamp
);
------------------------------------------------------------------------------------------------------------------------
insert into log_events_svc_post (id, description)
values (1, 'Post created'),
       (2, 'Post updated'),
       (3, 'Post deleted'),
       (4, 'Searched posts by keyword matching with titles or contents'),
       (-1, 'Trying to update post. Post does''t exist'),
       (-2, 'Trying to update post. User is trying to delete someone else''s post ');
------------------------------------------------------------------------------------------------------------------------