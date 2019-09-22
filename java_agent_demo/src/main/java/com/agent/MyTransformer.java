package com.agent;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

import java.io.ByteArrayInputStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

/**
 * 当碰到加载的类是 kite.attachapi.Person的时候，在其中的 test 方法开始时插入一条打印语句，打印内容是"动态插入的打印语句"，
 * 在test方法结尾处，打印返回值，其中$_就是返回值，这是 javassist 里特定的标示符。
 * @Author: xiaohuan
 * @Date: 2019-09-22 10:31
 */
public class MyTransformer implements ClassFileTransformer {
  @Override
  public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
    System.out.println("正在加载类："+ className);
    /*
    if (!"kite/attachapi/Person".equals(className)){
      return classfileBuffer;
    }
    */
    if(!className.toLowerCase().contains("person")){
      System.out.println("****");
      return classfileBuffer;
    }

    CtClass cl = null;
    try {
      System.out.println("----");
      ClassPool classPool = ClassPool.getDefault();
      cl = classPool.makeClass(new ByteArrayInputStream(classfileBuffer));
      System.out.println(cl);
      CtMethod ctMethod = cl.getDeclaredMethod("test");
      System.out.println("获取方法名称："+ ctMethod.getName());

      ctMethod.insertBefore("System.out.println(\" 动态插入的打印语句 \");");
      ctMethod.insertAfter("System.out.println($_);");

      byte[] transformed = cl.toBytecode();
      return transformed;
    }catch (Exception e){
      e.printStackTrace();

    }
    return classfileBuffer;
  }
}
