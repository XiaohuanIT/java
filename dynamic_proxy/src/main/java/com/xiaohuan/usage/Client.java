package com.xiaohuan.usage;

import com.xiaohuan.InvocationHandler;
import com.xiaohuan.three.Proxy3;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * @Author: xiaohuan
 * @Date: 2019-08-10 12:13
 */
public class Client {
  public static void  main(String[] args) throws IllegalAccessException, InstantiationException, IOException, NoSuchMethodException, InvocationTargetException, ClassNotFoundException {
    UserMgr mgr = new UserMgrImpl();
    InvocationHandler h = new TransactionHandler(mgr);
    UserMgr userMgr = (UserMgr) Proxy3.newProxyInstance(UserMgr.class, h);
    userMgr.addUser();
  }
}
