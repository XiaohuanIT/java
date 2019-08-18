package com.mixin;

import net.sf.cglib.proxy.Mixin;

import java.lang.reflect.Method;

/**
 * @Author: xiaohuan
 * @Date: 2019-08-18 20:49
 */
public class MixinDemo {
  public static void main(String[] args) {
    //接口数组
    Class<?>[] interfaces = new Class[] { MyInterfaceA.class, MyInterfaceB.class };
    //实例对象数组
    Object[] delegates = new Object[] { new MyInterfaceAImpl(), new MyInterfaceBImpl() };

    //Minix组合为o对象。
    Object o = Mixin.create(interfaces, delegates);
    MyInterfaceA a = (MyInterfaceA) o;
    a.methodA();
    MyInterfaceB b = (MyInterfaceB) o;
    b.methodB();
    System.out.println("\r\n输出Mixin对象的结构...");
    Class clazz = o.getClass();
    Method[] methods = clazz.getDeclaredMethods();
    for (int i = 0; i < methods.length; i++) {
      System.out.println(methods[i].getName());
    }
    System.out.println(clazz);
  }
}
