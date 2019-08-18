package com.demo1;

import net.sf.cglib.proxy.CallbackFilter;

import java.lang.reflect.Method;

/**
 * 回调过滤器类
 * @Author: xiaohuan
 * @Date: 2019-08-17 21:54
 */
public class DaoFilter implements CallbackFilter {
  @Override
  public int accept(Method method) {
    if("select".equalsIgnoreCase(method.getName())) {
      return 0;  //select方法使用callbacks[0]对象拦截
    } else if ("delete".equalsIgnoreCase(method.getName())) {
      return 1;  //delete方法使用callbacks[1]对象拦截
    }
    return 2; //使用callbacks[2]对象拦截
  }
}
