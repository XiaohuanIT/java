create database test_lock;

CREATE TABLE IF NOT EXISTS items (
  id      INTEGER NOT NULL,
  `name`   varchar(50) NOT NULL,
  price double NOT NULL,
  quantity INTEGER NOT NULL,
  version  INTEGER,
  PRIMARY KEY (id)
)ENGINE = INNODB;


CREATE TABLE IF NOT EXISTS orders (
  id      INTEGER primary key auto_increment,
  `item_id`   INTEGER NOT NULL,
  key item_id_index (`item_id`)
)ENGINE = INNODB;



insert into items(id, `name`, price, quantity, version) values(100, '100-item', 100, 100, 1);

