package com.demo1;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * 自定义回调类
 * @Author: xiaohuan
 * @Date: 2019-08-17 21:53
 */
public class MethodInterceptorImpl implements MethodInterceptor {
  @Override
  public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
    System.out.println("Before invoke " + method);
    //invokeSuper方法调用目标类的方法
    Object result = proxy.invokeSuper(obj, args); //原方法调用
    System.out.println("After invoke" + method);
    return result;
  }
}
