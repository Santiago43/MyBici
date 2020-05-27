use mysql;
select * from user;

create user andres@25.103.173.234;

grant all privileges on *.* to andres@25.103.173.234;

create user carlos@25.13.4.193;
grant all privileges on *.* to carlos@25.13.4.193;

create user carlos2@25.5.145.226;
grant all privileges on *.* to carlos2@25.5.145.226;