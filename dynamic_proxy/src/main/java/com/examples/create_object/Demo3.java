package com.examples.create_object;

import java.lang.reflect.InvocationTargetException;

/**
 * @Author: xiaohuan
 * @Date: 2019-08-12 21:12
 */
public class Demo3 {
  Long l;
  String s;
  int j;

  public Demo3(Long l, String s, int i){
    this.l = l;
    this.s = s;
    this.j = j;
  }

  public static void main(String args[]) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
    Class classToLoad = Demo3.class;

    Class[] cArg = new Class[3]; //Our constructor has 3 arguments
    cArg[0] = Long.class; //First argument is of *object* type Long
    cArg[1] = String.class; //Second argument is of *object* type String
    cArg[2] = int.class; //Third argument is of *primitive* type int

    Long l = new Long(88);
    String s = "text";
    int i = 5;

    Demo3 demo3New = (Demo3)classToLoad.getDeclaredConstructor(cArg).newInstance(l, s, i);
    System.out.println(demo3New.s);
  }
}
