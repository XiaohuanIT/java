package com.jdk_usage;

import com.xiaohuan.Moveable;
import com.xiaohuan.Tank;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * @Author: xiaohuan
 * @Date: 2019-07-28 10:54
 */
public class Client {
  public static void main(String[] args) throws IllegalAccessException, InstantiationException, IOException, NoSuchMethodException, InvocationTargetException, ClassNotFoundException {
    DynaProxyHello helloproxy = new DynaProxyHello();
    Moveable hello = new Tank();
    Moveable ihello = (Moveable) helloproxy.bind(hello);
    ihello.move();
  }
}
