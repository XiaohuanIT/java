package com.xiaohuan.usage;

/**
 * @Author: xiaohuan
 * @Date: 2019-08-10 12:09
 */
public class UserMgrImpl implements UserMgr{
  @Override
  public void addUser() {
    System.out.println("1. insert into user");
    System.out.println("2. log print operation");
  }
}
