package demo1;

import demo1.Dog;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @Author: xiaohuan
 * @Date: 2019-12-21 14:00
 */
public class Demo1 {

  public static void main(String[] args) {
    testFields();
  }

  public static void testFields() {
    try {
      System.out.println("Declared fields: ");
      Field[] fields = Dog.class.getDeclaredFields();
      for(int i = 0; i < fields.length; i++) {
        System.out.println(fields[i].getName()); // 此处结果是color, name, type, fur
      }

      System.out.println("Public fields: ");
      fields = Dog.class.getFields();
      for(int i = 0; i < fields.length; i++) {
        System.out.println(fields[i].getName()); // 此处结果是color, location
      }

      Dog dog = new Dog();
      dog.setAge(1);
      Method method = dog.getClass().getMethod("getAge", null);
      Object value = method.invoke(dog);
      System.out.println(value); // 此处结果是1
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
