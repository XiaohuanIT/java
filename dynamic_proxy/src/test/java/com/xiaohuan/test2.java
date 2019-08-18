package com.xiaohuan;

import java.lang.reflect.Method;

/**
 * @Author: xiaohuan
 * @Date: 2019-07-28 22:58
 */
public class test2 {
  public static void main(String[] args){
    Method[] methods = Moveable.class.getMethods();
    for(Method method : methods){
      System.out.println(method.getName());
    }
  }

  public void tttt(Class infce, InvocationHandler handler){
    try {
      Method[] methods = Moveable.class.getMethods();
      for(Method method : methods){
        System.out.println(method.getName());
        Method md = Moveable.class.getMethod(method.getName());
        handler.invoke(this, md);
      }
    }catch (Exception ex){
      System.err.println(ex.toString());
    }
  }
}
