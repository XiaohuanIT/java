MyBatis框架的核心功能其实不难，无非就是动态代理和jdbc的操作，难的是写出来可扩展，高内聚，低耦合的规范的代码。本文完成的Mybatis功能比较简单，代码还有许多需要改进的地方，大家可以结合[Mybatis源码](http://github.com/mybatis/mybatis-3)去动手完善。

## 一、Mybatis框架流程简介

![一、Mybatis框架流程简介](./_offline_resource/Mybatis框架流程简介.png)





在手写自己的Mybatis框架之前，我们先来了解一下Mybatis，它的源码中使用了大量的设计模式，阅读源码并观察设计模式在其中的应用，才能够更深入的理解源码（ref：[Mybatis源码解读-设计模式总结](http://www.crazyant.net/2022.html)）。我们对上图进行分析总结：

1. mybatis的配置文件有2类
   - mybatisconfig.xml，配置文件的名称不是固定的，**配置了全局的参数的配置**，全局只能有一个配置文件。
   - Mapper.xml **配置多个statemement，也就是多个sql**，整个mybatis框架中可以有多个Mappe.xml配置文件。
2. 通过mybatis配置文件得到SqlSessionFactory
3. 通过SqlSessionFactory得到SqlSession，用SqlSession就可以操作数据了。
4. SqlSession通过底层的Executor（执行器），执行器有2类实现：

![Executor](./_offline_resource/Executor执行器实现.png)

- 基本实现

- 带有缓存功能的实现

5.MappedStatement是通过Mapper.xml中定义statement生成的对象。

6.参数输入执行并输出结果集，无需手动判断参数类型和参数下标位置，且自动将结果集映射为Java对象

- HashMap，KV格式的数据类型
- Java的基本数据类型
- POJO，java的对象



## 二、梳理自己的Mybatis的设计思路

​    根据上文Mybatis流程，我简化了下，分为以下步骤：

![Mybatis的设计思路](./_offline_resource/Mybatis的设计思路.png)



### 1.读取xml文件，建立连接

​    从图中可以看出，MyConfiguration负责与人交互。待读取xml后，将属性和连接数据库的操作封装在MyConfiguration对象中供后面的组件调用。本文将使用dom4j来读取xml文件，它具有性能优异和非常方便使用的特点。

### 2.创建SqlSession，搭建Configuration和Executor之间的桥梁

​    我们经常在使用框架时看到Session，Session到底是什么呢？**一个Session仅拥有一个对应的数据库连接**。**类似于一个前段请求Request**，它可以直接调用exec(SQL)来执行SQL语句。从流程图中的箭头可以看出，MySqlSession的成员变量中必须得有MyExecutor和MyConfiguration去集中做调配，箭头就像是一种关联关系。我们自己的MySqlSession将有一个getMapper方法，然后使用动态代理生成对象后，就可以做数据库的操作了。 

###  3.创建Executor，封装JDBC操作数据库

​    Executor是一个执行器，负责SQL语句的生成和查询缓存（缓存还没完成）的维护，也就是jdbc的代码将在这里完成，不过本文只实现了单表，有兴趣的同学可以尝试完成多表。

### 4.创建MapperProxy，使用动态代理生成Mapper对象

​    我们只是希望**对指定的接口生成一个对象**，使得执行它的时候能运行一句sql罢了，而接口无法直接调用方法，所以这里使用动态代理生成对象，在**执行时**还是回到**MySqlSession中调用查询**，最终由**MyExecutor做JDBC查询**。这样设计是为了单一职责，可扩展性更强。



## 三、实现自己的Mybatis

具体代码见工程mybatis_implemention。

![自己实现的mybatis运行结果](./_offline_resource/自己实现的mybatis运行结果.png)





## 四、java操作mysql代码

```
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
 
import com.mysql.jdbc.Connection;
 
public class test {
 
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		String url = "jdbc:mysql://localhost:3306/test_db";
		String user = "root";
		String password = "12345678";
		java.sql.Connection myConnection = DriverManager.getConnection(url,user,password);
		Statement mystatement = myConnection.createStatement();
		ResultSet  rs = mystatement.executeQuery("  SELECT f_name,f_price FROM  test_db.fruits");
		while(rs.next()) {
			String fn = rs.getString("f_name");
			String fp = rs.getString("f_price");
			System.out.println(fn+"  "+fp);
		}
		if(mystatement != null) {
			mystatement.close();
			mystatement=null;
		}
		if(myConnection !=null) {
			myConnection.close();
			myConnection=null;
		}
	}
 
}
```







参考文章：

1、https://www.cnblogs.com/xdyixia/p/9420547.html

2、https://blog.csdn.net/qq_41573234/article/details/80618303