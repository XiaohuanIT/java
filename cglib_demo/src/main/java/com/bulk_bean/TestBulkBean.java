package com.bulk_bean;

import com.immutable_bean.SampleBean;
import net.sf.cglib.beans.BulkBean;

/**
 * @Author: xiaohuan
 * @Date: 2019-08-18 22:00
 */
public class TestBulkBean {
  public static void main(String[] args) {
    BulkBean bulkBean = BulkBean.create(SampleBean.class,
            new String[]{"getValue"},
            new String[]{"setValue"},
            new Class[]{String.class});
    SampleBean bean = new SampleBean();
    bean.setValue("Hello world");
    Object[] propertyValues = bulkBean.getPropertyValues(bean);
    System.out.println(bulkBean.getPropertyValues(bean).length);//1
    System.out.println(bulkBean.getPropertyValues(bean)[0]);//Hello world
    bulkBean.setPropertyValues(bean,new Object[]{"Hello cglib"});//Hello cglib
    System.out.println(bean.getValue());
  }
}
