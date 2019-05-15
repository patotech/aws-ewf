insert into role (id, name) values (1, 'Administrator');
insert into user (id, password, username) values ( 1, '$2a$10$uQPwowHSr3M8jqzUO/uB/unrkYX6bCdz/4A7DRLZ9Q5hisqMmbJ.q', 'admin@demo.aws');
insert into user_roles (users_id, roles_id) values (1,1);