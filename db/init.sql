create user 'root'@'%' identified by 'mysql123';
grant all on *.* to 'root'@'%';
create database if not exists reservation_system;
create database if not exists reservation_system_test;
flush privileges;
