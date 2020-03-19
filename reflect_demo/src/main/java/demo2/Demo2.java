import demo1.Dog;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @Author: xiaohuan
 * @Date: 2019-12-21 15:32
 */
public class Demo2 {
  public static void main(String[] args) {
    testFields();
  }

  public static void testFields() {
    Dog dog = new Dog();
    dog.setAge(1);
    dog.getClass().getDeclaredField()

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


    Method method = dog.getClass().getMethod("getAge", null);
    Object value = method.invoke(dog);
    System.out.println(value); // 此处结果是1

  }
}
