insert into usr (id, username, password, active)
values (8, 'newadmin', '123', true);

insert into user_role (user_id, roles)
values (8, 'USER'), (8, 'ADMIN');

ALTER SEQUENCE hibernate_sequence RESTART WITH 9;

update usr set password = crypt(password, gen_salt('bf', 8));