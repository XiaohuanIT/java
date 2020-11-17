```
DROP DATABASE IF EXISTS javacode2018;
CREATE DATABASE javacode2018;
USE javacode2018;
DROP TABLE IF EXISTS t_lock;
create table t_lock(
  lock_key varchar(32) PRIMARY KEY NOT NULL COMMENT '锁唯一标志',
  request_id varchar(64) NOT NULL DEFAULT '' COMMENT '用来标识请求对象的',
  lock_count INT NOT NULL DEFAULT 0 COMMENT '当前上锁次数',
  timeout BIGINT NOT NULL DEFAULT 0 COMMENT '锁超时时间',
  version INT NOT NULL DEFAULT 0 COMMENT '版本号，每次更新+1'
)COMMENT '锁信息表';
```