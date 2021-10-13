背景：我们在提供RPC接口时候，对于入参，出参，最好都重写toString方法。这样，可以方便调用方在使用时候，直接打印日志。



如果遇到某RPC方法的入参，是没有重写toString方法，导致直接打印对象，打印出来的是地址，那么我们怎么较好地解决此问题呢？



```java

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 当依赖的rpc接口的入参，没有重写toString方法，需要打印所有的入参，怎么办
 */
public class ToStringDemo {

    public static void main(String[] args) {
        InnerObject innerObject = new InnerObject();
        innerObject.setName("test-test");
        System.out.println(innerObject);
        System.out.println(innerObject.toString());

        System.out.println(ToStringBuilder.reflectionToString(innerObject));

        System.out.println(ReflectionToStringBuilder.toString(innerObject));
    }

    public static class InnerObject {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
```

输出是

```
com.lambda.ToStringDemo$InnerObject@6ce253f1
com.lambda.ToStringDemo$InnerObject@6ce253f1
com.lambda.ToStringDemo$InnerObject@6ce253f1[name=test-test]
com.lambda.ToStringDemo$InnerObject@6ce253f1[name=test-test]
```





