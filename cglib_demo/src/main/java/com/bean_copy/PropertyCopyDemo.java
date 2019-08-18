package com.bean_copy;

import net.sf.cglib.beans.BeanCopier;

/**
 * @Author: xiaohuan
 * @Date: 2019-08-18 20:59
 */
public class PropertyCopyDemo {
  public static void main(String[] args) {
    //两个对象
    Other other = new Other("test", "1234");
    Myth myth = new Myth();
    System.out.println(other);
    System.out.println(myth);
    //构建BeanCopier,并copy对象的属性值。
    BeanCopier copier = BeanCopier.create(Other.class, Myth.class, false);
    copier.copy(other, myth, null);
    System.out.println(other);
    System.out.println(myth);
  }
}
