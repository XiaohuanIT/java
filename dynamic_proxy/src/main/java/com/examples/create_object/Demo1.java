package com.examples.create_object;

/**
 * @Author: xiaohuan
 * @Date: 2019-08-12 20:20
 */
public class Demo1 {
  int x = 10;
  public static void main(String args[]) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
    Class myClass = Class.forName("com.examples.create_object.Demo1");

    // 不使用强制类型转换的方式
    Demo1 myObj = Demo1.class.newInstance();
    // 使用泛型
    Class<Demo1> c = myClass;

    Object obj = myClass.newInstance(); // 这里newInstance，只有无参的方法。

    Demo1 d1 = (Demo1) obj;
    d1.x = 10;
    System.out.println("Object created and value is " + d1.x);	  // prints 10
  }
}
