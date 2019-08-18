package com.examples.create_object.clone;

/**
 * @Author: xiaohuan
 * @Date: 2019-08-12 20:37
 */
public class B implements Cloneable {
  private A objectA;

  public A getObjectA() {
    return objectA;
  }

  public void setObjectA(A objectA) {
    this.objectA = objectA;
  }

  public B clone() throws CloneNotSupportedException {
    return (B)super.clone();
  }

  @Override
  public String toString(){
    return String.valueOf(objectA.getIntValue());
  }
}
