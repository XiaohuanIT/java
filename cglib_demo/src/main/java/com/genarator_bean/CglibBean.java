package com.genarator_bean;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import net.sf.cglib.beans.BeanGenerator;
import net.sf.cglib.beans.BeanMap;

/**
 * 动态实体bean
 * @Author: xiaohuan
 * @Date: 2019-08-18 21:04
 */

public class CglibBean {
  /**
   * 实体Object
   */
  public Object object = null;

  /**
   * 属性map
   */
  public BeanMap beanMap = null;
  public CglibBean() {
    super();
  }

  @SuppressWarnings("unchecked")
  public CglibBean(Map<String, Class> propertyMap) {
    this.object = generateBean(propertyMap);
    this.beanMap = BeanMap.create(this.object);
  }

  /**
   * 给bean属性赋值
   * @param property 属性名
   * @param value 值
   */
  public void setValue(String property, Object value) {
    beanMap.put(property, value);
  }

  /**
   * 通过属性名得到属性值
   * @param property 属性名
   */
  public Object getValue(String property) {
    return beanMap.get(property);
  }

  /**
   * 得到该实体bean对象。
   */
  public Object getObject() {
    return this.object;
  }

  /**
   * 生成Bean
   * @param propertyMap
   * @return
   */
  @SuppressWarnings("unchecked")
  private Object generateBean(Map<String, Class> propertyMap) {
    BeanGenerator generator = new BeanGenerator();
    Set keySet = propertyMap.keySet();
    for (Iterator i = keySet.iterator(); i.hasNext();) {
      String key = (String) i.next();
      generator.addProperty(key, (Class) propertyMap.get(key));
    }
    return generator.create();
  }
}
