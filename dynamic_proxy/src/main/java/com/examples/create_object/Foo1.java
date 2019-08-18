package com.examples.create_object;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @Author: xiaohuan
 * @Date: 2019-08-12 21:37
 */
public class Foo1 {

  public static void main(String args[]) throws ClassNotFoundException {
    Class<Foo> clazz = (Class<Foo>) Class.forName("com.examples.create_object.Foo");
    Constructor<Foo> constructor = null;
    try {
      constructor = clazz.getConstructor();
    } catch (NoSuchMethodException e) {
      e.printStackTrace();
    }

    try {
      Foo foo = constructor.newInstance();
    } catch (InstantiationException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      System.out.println("handle 3 called");
      e.printStackTrace();
    }
  }
}
