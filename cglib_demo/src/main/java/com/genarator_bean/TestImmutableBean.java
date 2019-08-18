package com.genarator_bean;

import net.sf.cglib.beans.BeanGenerator;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @Author: xiaohuan
 * @Date: 2019-08-18 21:31
 */
public class TestImmutableBean {
  public static void main(String[] args) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
    BeanGenerator beanGenerator = new BeanGenerator();
    beanGenerator.addProperty("value",String.class);
    Object myBean = beanGenerator.create();
    Method setter = myBean.getClass().getMethod("setValue",String.class);
    setter.invoke(myBean,"Hello cglib");

    Method getter = myBean.getClass().getMethod("getValue");
    System.out.println(getter.invoke(myBean));
  }
}
