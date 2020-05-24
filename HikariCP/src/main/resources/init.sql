CREATE DATABASE `persontest` CHARACTER SET 'utf8';

use persontest;

CREATE TABLE `persontest`.`personinfo`  (
  `id` int(0) NOT NULL,
  `username` varchar(255) NULL,
  PRIMARY KEY (`id`)
);

INSERT INTO personinfo(id,username) VALUES(0,'CSDN yunlingfly');
INSERT INTO personinfo(id,username) VALUES(1,'dakl');
INSERT INTO personinfo(id,username) VALUES(2,'dhasjk');