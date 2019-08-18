package com.xiaohuan.three;

import com.xiaohuan.InvocationHandler;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * @Author: xiaohuan
 * @Date: 2019-07-28 10:42
 */
public class Proxy3 {
  // jdk6 Compiler;  CGLib, ASM

  // 能够动态指定对某一东西进行动态代理
  public static Object newProxyInstance(Class infce, InvocationHandler handler) throws IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException, ClassNotFoundException, IOException {
    String rt = "\r\n";
    String methodString = "";
    Method[] methods =infce.getMethods();
    /*
    for(Method method: methods){
      methodString += "@Override" + rt +
              "public void " + method.getName() + "(){" + rt +
              "    long start = System.currentTimeMillis();"+ rt +
              "    t." +  method.getName() + "();" + rt +
              "    long end = System.currentTimeMillis();"+ rt +
              "    System.out.println(\"time:\" + (end-start));"+ rt +
              "}";
    }
    */

    for(Method method: methods){
      methodString += "    @Override" + rt +
                      "    public void " + method.getName() + "() {" + rt +
                      "        try {" + rt +
                      "            Method md = " + infce.getName() + ".class.getMethod(\"" + method.getName() +  "\");" + rt +
                      "            h.invoke(this, md);" + rt +
                      "        }catch (Exception ex){" + rt +
                      "            System.err.println(ex.toString());" + rt +
                      "        }" + rt +
                      "    }";
    }


    String src =
            "package com.xiaohuan;"  + rt +
            "import java.lang.reflect.Method;" + rt +
            "public class TankTimeProxy implements "+ infce.getName() + "{" + rt +
            "    Moveable t;"+ rt +

            "    public TankTimeProxy(InvocationHandler h){"+ rt +
            "        super();"+ rt +
            "        this.h = h;"+ rt +
            "    }"+ rt +
            "    com.xiaohuan.InvocationHandler h;" + rt +
            methodString + rt +
            "}";


    String path = "/Users/yangxiaohuan/Downloads/java_tmp/";
    //System.out.println(System.getProperty("user.dir"));
    String filename = path + "com/xiaohuan/TankTimeProxy.java";
    System.out.println(filename);
    File file = new File(filename);
    FileWriter fw = new FileWriter(file);
    fw.write(src);
    fw.flush();
    fw.close();

    //compile
    JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
    System.out.println(compiler.getClass().getName());
    StandardJavaFileManager fileMgr = compiler.getStandardFileManager(null, null, null);
    Iterable<? extends JavaFileObject> units = fileMgr.getJavaFileObjects(filename);
    JavaCompiler.CompilationTask compileTask = compiler.getTask(null , fileMgr, null,null, null,units);
    compileTask.call();
    fileMgr.close();

    // load into memory and create an instance
    //URL[] urls = new URL[]{new URL("file:/" + System.getProperty("user.dir") + "/src/main/java/")};
    // 一定注意，file后面只有一个斜线，不能是两条斜线
    URL[] urls = new URL[]{new URL("file:" + path)};
    //URL[] urls = {file.toURI().toURL()};



    //URLClassLoader ul = new URLClassLoader(urls, Proxy2.class.getClassLoader());
    URLClassLoader ul = new URLClassLoader(urls);
    Class c = ul.loadClass("com.xiaohuan.TankTimeProxy");

    //ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    //Class c = classLoader.loadClass("com.xiaohuan.TankTimeProxy");

    System.out.println(c);

    Constructor ctr = c.getConstructor(InvocationHandler.class);
    return ctr.newInstance(handler);
  }

}
