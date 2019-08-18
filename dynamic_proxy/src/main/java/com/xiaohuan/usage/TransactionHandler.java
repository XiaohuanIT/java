package com.xiaohuan.usage;

import com.xiaohuan.InvocationHandler;

import java.lang.reflect.Method;

/**
 * @Author: xiaohuan
 * @Date: 2019-08-10 12:11
 */
public class TransactionHandler implements InvocationHandler {
  private Object target;

  public TransactionHandler(Object obj){
    this.target = obj;
  }

  @Override
  public void invoke(Object obj, Method method) {
    System.out.println("Transaction start");
    try {
      method.invoke(target);
    }catch (Exception ex){
      ex.printStackTrace();
    }
    System.out.println("Transaction end");
  }
}
