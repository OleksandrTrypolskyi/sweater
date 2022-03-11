insert into usr (id, username, password, active)
values (7, 'admin', '123', true);

insert into user_role (user_id, roles)
values (7, 'USER'), (7, 'ADMIN');

ALTER SEQUENCE hibernate_sequence RESTART WITH 8;