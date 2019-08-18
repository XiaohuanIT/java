package com.examples.create_object;

import java.io.IOException;

/**
 * @Author: xiaohuan
 * @Date: 2019-08-12 21:37
 */
public class Foo {
  public Foo() throws IOException {
    throw new IOException();
  }

  public static void main(String args[]) throws ClassNotFoundException {
    Class<Foo> clazz = (Class<Foo>) Class.forName("com.examples.create_object.Foo");

    /**
     * Calling this will result in a IOException being thrown - problem is that your code does not handle it,
     * neither handle 1 nor handle 2 will catch it.
     */
    try {
      clazz.newInstance();
    } catch (InstantiationException e) {
      // handle 1
    } catch (IllegalAccessException e) {
      // handle 2
    }
  }
}
