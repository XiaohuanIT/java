
乐观锁、悲观锁，java代码的实现。具体的可以参考[探索Mysql锁机制-乐观锁&悲观锁](./探索Mysql锁机制-乐观锁&悲观锁.pdf)


初始时候，数据如下：

```
mysql> select * from items;
+-----+----------+-------+----------+---------+
| id  | name     | price | quantity | version |
+-----+----------+-------+----------+---------+
| 100 | 100-item |   100 |      100 |       1 |
+-----+----------+-------+----------+---------+
1 row in set (0.00 sec)
```

执行test目录下的ItemsServiceTest之后，数据如下：
```
mysql> select * from items;
+-----+----------+-------+----------+---------+
| id  | name     | price | quantity | version |
+-----+----------+-------+----------+---------+
| 100 | 100-item |   100 |      -14 |       1 |
+-----+----------+-------+----------+---------+
1 row in set (0.00 sec)
```

没有加任何锁，发生了超卖。



#### 悲观锁
在命令行中执行（不开启事务，直接执行提交）

```
mysql> update items set quantity=100 where id=100;
Query OK, 1 row affected (0.04 sec)
Rows matched: 1  Changed: 1  Warnings: 0

mysql> select * from items;
+-----+----------+-------+----------+---------+
| id  | name     | price | quantity | version |
+-----+----------+-------+----------+---------+
| 100 | 100-item |   100 |      100 |       1 |
+-----+----------+-------+----------+---------+
1 row in set (0.00 sec)
```

执行test目录下的 ItemsServiceTestWithPessimisticLock之后，查询数量

```
mysql> select * from items;
+-----+----------+-------+----------+---------+
| id  | name     | price | quantity | version |
+-----+----------+-------+----------+---------+
| 100 | 100-item |   100 |        0 |       1 |
+-----+----------+-------+----------+---------+
1 row in set (0.00 sec)
```

#### 乐观锁

```
mysql> update items set quantity=100 where id=100;
Query OK, 1 row affected (0.21 sec)
Rows matched: 1  Changed: 1  Warnings: 0
```

执行test目录下的ItemsServiceTestWithOptimisticLock之后
查询数据

```
mysql> select * from items;
+-----+----------+-------+----------+---------+
| id  | name     | price | quantity | version |
+-----+----------+-------+----------+---------+
| 100 | 100-item |   100 |       86 |      15 |
+-----+----------+-------+----------+---------+
1 row in set (0.00 sec)
```



主要参考
https://www.jianshu.com/p/ed896335b3b4、
https://www.cnblogs.com/cyhbyw/p/8869855.html、https://github.com/cyhbyw/cyh_Spring_IsolationConcurrencyTransaction
这两个网页做的实验。
