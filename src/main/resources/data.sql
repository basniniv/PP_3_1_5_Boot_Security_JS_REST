INSERT INTO role (id, rolename)
VALUES (1, 'ROLE_USER'),
       (2, 'ROLE_ADMIN');


INSERT INTO user (email, last_name, password, username, age)
VALUES ('basnin.iv@gmail.com'
       ,'basnin'
       ,'$2a$12$YDzcX1BLOn53cTCk/wl8AuCc535nTClkCgw5z9kBkG3B665xMFfRa'
           # password admin
       ,'admin',
        37);

insert into user_role (user_id, role_id)
values (1, 1),
       (1, 2);

select *
from user;

select *
from user_role;

# truncate user_role;
#
# delete
# from user;
#
# alter table user
#     auto_increment = 1;