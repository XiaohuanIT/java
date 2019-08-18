package com.examples.create_object.clone;

/**
 * @Author: xiaohuan
 * @Date: 2019-08-12 20:36
 */
public class A implements Cloneable{
  private int intValue;

  public int getIntValue() {
    return intValue;
  }

  public void setIntValue(int intValue) {
    this.intValue = intValue;
  }

  public A clone() throws CloneNotSupportedException {
    return (A)super.clone();
  }

  @Override
  public String toString(){
    return String.valueOf(intValue);
  }
}
