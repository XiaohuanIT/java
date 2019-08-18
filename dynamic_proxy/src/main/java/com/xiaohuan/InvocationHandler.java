package com.xiaohuan;

import java.lang.reflect.Method;

/**
 * @Author: xiaohuan
 * @Date: 2019-07-30 23:06
 */
public interface InvocationHandler {
  void invoke(Object obj, Method method);
}
