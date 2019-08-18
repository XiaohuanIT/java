package com.immutable_bean;

import net.sf.cglib.beans.ImmutableBean;

/**
 * @Author: xiaohuan
 * @Date: 2019-08-18 21:27
 */
public class SampleBean {
  private String value;

  public SampleBean() {
  }

  public SampleBean(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public static void main(String[] args) {
    SampleBean bean = new SampleBean();
    bean.setValue("Hello world");
    SampleBean immutableBean = (SampleBean) ImmutableBean.create(bean); //创建不可变类
    System.out.println(immutableBean.getValue());//Hello world
    bean.setValue("Hello world, again"); //可以通过底层对象来进行修改
    System.out.println(immutableBean.getValue());//Hello world, again
    immutableBean.setValue("Hello cglib"); //直接修改将throw exception
  }
}
