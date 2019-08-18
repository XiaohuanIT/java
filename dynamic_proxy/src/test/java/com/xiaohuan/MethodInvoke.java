package com.xiaohuan;

import java.lang.reflect.Method;

/**
 * @Author: xiaohuan
 * @Date: 2019-08-09 17:01
 */

public class MethodInvoke {

  public static void main(String[] args) throws Exception {
    Method animalMethod = Animal.class.getDeclaredMethod("print");
    Method catMethod = Cat.class.getDeclaredMethod("print");
    Method dogPrint = Dog.class.getDeclaredMethod("print", String.class);

    Animal animal = new Animal();
    Cat cat = new Cat();
    String methodArg = "a";
    animalMethod.invoke(cat);
    animalMethod.invoke(animal);

    Dog dog = new Dog();
    Object result = dogPrint.invoke(dog, methodArg);
    System.out.println(result);

    catMethod.invoke(cat);
    catMethod.invoke(animal);


  }

}

class Animal {

  public void print() {
    System.out.println("Animal.print()");
  }

}

class Cat extends Animal {

  @Override
  public void print() {
    System.out.println("Cat.print()");
  }

}

class Dog extends Animal {
  @Override
  public void print() {
    System.out.println("Cat.print()");
  }

  public int print(String a){
    System.out.println(a);
    return 5;
  }
}
