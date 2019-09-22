package javassit_demo;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;

import java.io.FileOutputStream;

/**
 * @Author: xiaohuan
 * @Date: 2019-09-22 22:18
 */
public class Demo2 {
  public static void main(String[] args) {
    ClassPool pool = ClassPool.getDefault();
    CtClass ctClass = pool.makeClass("com.czp.CtClassTest");
    ctClass.stopPruning(true);
    try {
      //自定义属性
      ctClass.addField(CtField.make("private int age;", ctClass));
      ctClass.addMethod(CtMethod.make("public void setAge(int age){this.age = age;}", ctClass));
      ctClass.addMethod(CtMethod.make("public int getAge(){return this.age;}", ctClass));
      //获取字节数组写入到指定文件
      byte[] byteArray = ctClass.toBytecode();
      FileOutputStream output = new FileOutputStream("/Users/yangxiaohuan/Downloads/CtClassTest.class");
      output.write(byteArray);
      output.close();


      if(ctClass.isFrozen()){//如果class文件被冻结，已经被jvm加载
        ctClass.defrost();//解冻
      }
      //在class Pool中获取到对应.class文件
      ctClass = pool.get("com.czp.CtClassTest");
      //同上
      ctClass.addField(CtField.make("private String sex;", ctClass));
      ctClass.addField(CtField.make("private String name;", ctClass));
      byteArray = ctClass.toBytecode();
      output = new FileOutputStream("/Users/yangxiaohuan/Downloads/CtClassTest.class");
      output.write(byteArray);
      output.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
