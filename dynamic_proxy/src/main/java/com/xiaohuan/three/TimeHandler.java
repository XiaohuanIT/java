package com.xiaohuan.three;

import com.xiaohuan.InvocationHandler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @Author: xiaohuan
 * @Date: 2019-07-30 23:07
 */
public class TimeHandler implements InvocationHandler {
  private Object target;

  public TimeHandler(Object target) {
    this.target = target;
  }

  // 对任意方法进行自定义处理。 对哪个对象进行调用
  @Override
  public void invoke(Object obj, Method method) {
    long start = System.currentTimeMillis();
    System.out.println("start time:"+start);
    System.out.println(obj.getClass().getName());
    try {
      //method.invoke(obj, new Object[]{});
      method.invoke(target);
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      e.printStackTrace();
    }
    long end = System.currentTimeMillis();
    System.out.println("end time:"+end);
    System.out.println("time:" + (end-start));
  }
}
