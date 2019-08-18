package com.examples.create_object.clone;

/**
 * @Author: xiaohuan
 * @Date: 2019-08-12 20:38
 */
public class C implements Cloneable{
  private A objectA;

  public A getObjectA() {
    return objectA;
  }

  public void setObjectA(A objectA) {
    this.objectA = objectA;
  }

  public C clone() throws CloneNotSupportedException {
    C newC = (C)super.clone();
    newC.objectA =(A) objectA.clone();
    return newC;
  }

  @Override
  public String toString(){
    return String.valueOf(objectA.getIntValue());
  }

}
