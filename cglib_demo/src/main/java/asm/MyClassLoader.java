package asm;

/**
 * @Author: xiaohuan
 * @Date: 2019-08-17 21:44
 */
public class MyClassLoader {
  public Class<?> defineClass(String name, byte[] b) {
    // ClassLoader是个抽象类，而ClassLoader.defineClass 方法是protected的
    // 所以我们需要定义一个子类将这个方法暴露出来
    return null;
    //return super.defineClass(name, b, 0, b.length);
  }
}
