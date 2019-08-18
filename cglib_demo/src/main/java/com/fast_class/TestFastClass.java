package com.fast_class;

import com.immutable_bean.SampleBean;
import net.sf.cglib.reflect.FastClass;
import net.sf.cglib.reflect.FastMethod;

import java.lang.reflect.InvocationTargetException;

/**
 * @Author: xiaohuan
 * @Date: 2019-08-18 21:34
 */
public class TestFastClass {
  public static void main(String[] args) throws InvocationTargetException {
    FastClass fastClass = FastClass.create(SampleBean.class);
    FastMethod fastMethod = fastClass.getMethod("getValue",new Class[0]);
    SampleBean bean = new SampleBean();
    bean.setValue("Hello world");
    System.out.println(fastMethod.invoke(bean, new Object[0]));
  }
}
