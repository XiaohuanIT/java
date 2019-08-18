import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @Author: xiaohuan
 * @Date: 2019-08-12 22:03
 */
public class SampleClass2 {
  public void test(){
    while (true){
      System.out.println("SampleClass2 print");
    }
  }

  public static void main(String[] args) {
    Enhancer enhancer = new Enhancer();
    enhancer.setSuperclass(SampleClass2.class);
    enhancer.setCallback(new MethodInterceptor() {
      @Override
      public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        System.out.println("before method run...");
        Object result = proxy.invokeSuper(obj, args);
        System.out.println("after method run...");
        return result;
      }
    });
    SampleClass2 sample = (SampleClass2) enhancer.create();
    sample.test();
  }
}
