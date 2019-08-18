import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.FixedValue;

/**
 * @Author: xiaohuan
 * @Date: 2019-08-17 21:10
 */
public class SampleClass1 {
  public String test(String input){
    return "  hello world";
  }

  public static void main(String[] args) {
    Enhancer enhancer = new Enhancer();
    enhancer.setSuperclass(SampleClass1.class);
    enhancer.setCallback(new FixedValue() {
      @Override
      public Object loadObject() throws Exception {
        return "Hello cglib";
      }
    });
    SampleClass1 proxy = (SampleClass1) enhancer.create();
    System.out.println(proxy.test(null)); //拦截test，输出Hello cglib
    System.out.println(proxy.toString());
    System.out.println(proxy.getClass());
    System.out.println(proxy.hashCode());

    /**
     * FixedValue用来对所有拦截的方法返回相同的值，从输出我们可以看出来，Enhancer对非final方法test()、toString()、hashCode()进行了拦截，没有对getClass进行拦截。由于hashCode()方法需要返回一个Number，但是我们返回的是一个String，这解释了上面的程序中为什么会抛出异常。

     结果
     Hello cglib
     Hello cglib
     class SampleClass1$$EnhancerByCGLIB$$61fb0ebc
     Exception in thread "main" java.lang.ClassCastException: java.lang.String cannot be cast to java.lang.Number
     	   at SampleClass1$$EnhancerByCGLIB$$61fb0ebc.hashCode(<generated>)
     	   at SampleClass1.main(SampleClass1.java:26)
     */

  }
}
