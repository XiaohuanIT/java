package javassit_demo;

import javassist.*;
import javassist.bytecode.AccessFlag;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @Author: xiaohuan
 * @Date: 2019-09-22 22:12
 */
public class DynamicGenerateClass {
  public static void main(String[] args) {

    ClassPool pool = ClassPool.getDefault();
    CtClass cc = pool.makeClass("com.robin.assit.test.Retangle");// 创建类
    cc.setInterfaces(new CtClass[] { pool.makeInterface("java.lang.Cloneable") });// 让类实现Cloneable接口
    try {
      CtField f = new CtField(CtClass.intType, "id", cc);
      // 生成一个int 类型的id属性
      f.setModifiers(AccessFlag.PUBLIC);// 设置访问修饰符
      cc.addField(f);// 将属性设置到类上
      // 添加构造方法
      CtConstructor constructor = CtNewConstructor.make("public Retangle(int id){this.id=id;}", cc);
      cc.addConstructor(constructor);
      // 添加方法
      CtMethod alohaMethod = CtNewMethod
              .make("public void aloha(String hello){" + "System.out.println(hello);" + "}", cc);
      cc.addMethod(alohaMethod);

      // 将生成的.class 文件保存到磁盘
      //cc.writeFile();

      //验证结果
      Class ct=cc.toClass();
      Field[] fields = ct.getFields();
      System.out.println("attribute name:" + fields[0].getName() + ",attribute type:" + fields[0].getType());
      Method aloha=ct.getMethod("aloha",new Class[] {String.class});

      //动态调用
      Constructor<?> con=ct.getConstructor(int.class);
      aloha.invoke(con.newInstance(1), "hello world");


    } catch (CannotCompileException e) {
      e.printStackTrace();
    } catch (NoSuchMethodException e) {
      e.printStackTrace();
    } catch (SecurityException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (IllegalArgumentException e) {
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      e.printStackTrace();
    } catch (InstantiationException e) {
      e.printStackTrace();
    }

  }
}
