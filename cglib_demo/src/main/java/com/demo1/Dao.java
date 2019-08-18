package com.demo1;

/**
 * @Author: xiaohuan
 * @Date: 2019-08-17 21:53
 */
public class Dao {
  public void select() {
    System.out.println("select 1 from dual");
  }

  public void insert() {
    System.out.println("insert into ...");
  }

  //public void delete() {
  public final void delete() {
    System.out.println("delete from ...");
  }
}
