package com.jdk_usage;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @Author: xiaohuan
 * @Date: 2019-08-10 18:43
 */
public class DynaProxyHello  implements InvocationHandler {
  private Object target;

  public DynaProxyHello(){
  }

  public Object bind(Object delegate) {
    this.target = delegate;
    return Proxy.newProxyInstance(this.target.getClass().getClassLoader(), this.target.getClass().getInterfaces(), this);
  }

  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    //result是：target对象，调用method的方法之后，返回的结果。如果method的返回类型为void，那么result就是null；否则result类型就是method方法的返回类型
    System.out.println(method.getName()+"方法开始执行,time:"+ System.currentTimeMillis());
    Object result = method.invoke(target, args);
    System.out.println(method.getName()+"方法执行结束,time:"+ System.currentTimeMillis());
    return result;
  }
}
