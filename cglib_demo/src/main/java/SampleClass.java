import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @Author: xiaohuan
 * @Date: 2019-08-12 22:03
 */
public class SampleClass {
  public void test(){
    System.out.println("hello world");
  }

  public static void main(String[] args) {
    Enhancer enhancer = new Enhancer();
    enhancer.setSuperclass(SampleClass.class);
    enhancer.setCallback(new MethodInterceptor() {
      @Override
      public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        System.out.println("before method run...");
        Object result = proxy.invokeSuper(obj, args);
        System.out.println("after method run...");
        return result;
      }
    });
    SampleClass sample = (SampleClass) enhancer.create();
    sample.test();
  }


  /**
   * 结果：
   before method run...
   hello world
   after method run...
   */

}
