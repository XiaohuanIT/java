package com.demo1;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @Author: xiaohuan
 * @Date: 2019-08-17 21:54
 */
public class DaoAnotherProxy implements MethodInterceptor {
  @Override
  public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
    System.out.println("开始intercept");
    Object result = proxy.invokeSuper(obj, args);
    System.out.println("结束intercept");
    return result;
  }
}
