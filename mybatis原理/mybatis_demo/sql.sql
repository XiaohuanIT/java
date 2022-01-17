create database my_test default character set utf8mb4;

create table `user_info` (
 `id` bigint NOT NULL AUTO_INCREMENT,
 `age` int(11) default NULL,
 `name` varchar(32),
 primary key (`id`)
) engine = innodb auto_increment=1 default charset = utf8mb4 collate = utf8mb4_general_ci;


use my_test;

insert into `user_info`(`id`, `age`, `name`) values (1,1,'yang');

