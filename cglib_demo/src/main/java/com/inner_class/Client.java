package com.inner_class;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.NoOp;

import java.lang.reflect.Constructor;
import java.util.Arrays;

/**
 * @Author: xiaohuan
 * @Date: 2019-08-18 22:15
 */
public class Client {
  public static void main(String[] args) throws ClassNotFoundException {
    Class inner = Class.forName("com.inner_class.Target$Student");
    //获取内部类构造函数
    Constructor[] constructors = inner.getDeclaredConstructors();
    for(Constructor c : constructors) {
      System.out.println(Arrays.toString(c.getParameterTypes()));////输出[class cglib.constructionTest.Target] 证明形参是外部类
    }
    Enhancer enhancer = new Enhancer();
    enhancer.setSuperclass(Target.Student.class);
    enhancer.setCallback(NoOp.INSTANCE);
    Target.Student student = (Target.Student) enhancer.create();
    System.out.println(student.name);
  }
}
