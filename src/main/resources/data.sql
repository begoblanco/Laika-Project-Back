
-- Users
INSERT INTO users (user_id, username, password)
 VALUES (default, 'user', '$2a$12$8LegtLQWe717tIPvZeivjuqKnaAs5.bm0Q05.5GrAmcKzXw2NjoUO');

 -- Roles
INSERT INTO roles (role_id, name)
 VALUES (default, 'ROLE_USER');

 -- Roles_Users
INSERT INTO role_users (role_id, user_id) 
VALUES (1, 1);