package com.xiaohuan.three;

import com.xiaohuan.Moveable;
import com.xiaohuan.Tank;
import com.xiaohuan.InvocationHandler;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * @Author: xiaohuan
 * @Date: 2019-07-28 10:54
 */
public class Client {
  public static void main(String[] args) throws IllegalAccessException, InstantiationException, IOException, NoSuchMethodException, InvocationTargetException, ClassNotFoundException {
    // 可以对任意的
    Tank t = new Tank(); // 被代理对象
    InvocationHandler h = new TimeHandler(t); // 代理的处理逻辑
    Moveable m = (Moveable) Proxy3.newProxyInstance(Moveable.class, h);
    m.move();
  }
}
