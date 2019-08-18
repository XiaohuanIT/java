package com.examples.create_object.clone;

/**
 * @Author: xiaohuan
 * @Date: 2019-08-12 20:26
 */
public class Demo2 implements Cloneable{
  public static void main(String args[]) throws CloneNotSupportedException {
    A a1 = new A();
    a1.setIntValue(100);
    B b1 = new B();
    b1.setObjectA(a1);

    B b2 = b1.clone();
    A a2= b2.getObjectA();
    a2.setIntValue(99);
    System.out.println(b1.toString());
    System.out.println(b2.toString());

    // 深度clone
    C c1 = new C();
    c1.setObjectA(a1);

    C c2 = c1.clone();
    A a3 = c2.getObjectA();
    a3.setIntValue(98);
    System.out.println(c1.toString());
    System.out.println(c2.toString());

  }
}
