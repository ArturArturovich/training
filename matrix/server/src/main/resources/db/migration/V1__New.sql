-- "password" bcrypted
insert into users (email, username, password, enabled, created_at, updated_at, created_by, updated_by)
values ('coder@1dea.ru', 'admin', '$2a$10$Pu/AcuLqbVTwrv54ZMbdPe8Nh4oyKStsAPSVKCI/dZFhOCpO/BKgO', 1, now(), now(), 1, 1);

insert into roles (name, display_name, description, created_at, updated_at, created_by, updated_by)
values ('ADMIN', 'ADMIN', 'ADMIN', now(), now(), 1, 1);

insert into user_role(user_id, role_id) select 1, id from roles;

insert into permissions (name, description, created_at, updated_at, created_by, updated_by)
values ('administer users', 'administer users', now(), now(), 1, 1),
       ('administer roles', 'administer roles', now(), now(), 1, 1),
       ('administer permissions', 'administer permissions', now(), now(), 1, 1),
       ('own profile', 'own profile', now(), now(), 1, 1),

       ('administer nodes', 'administer nodes', now(), now(), 1, 1),
       ('view Node', 'view Node', now(), now(), 1, 1),
       ('create Node', 'create Node', now(), now(), 1, 1),
       ('update Node', 'update Node', now(), now(), 1, 1),
       ('delete Node', 'delete Node', now(), now(), 1, 1);

insert into role_permission(role_id, permission_id) select 1, id from permissions;
