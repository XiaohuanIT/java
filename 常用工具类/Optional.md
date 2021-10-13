



#### map

避免使用if else的判断。而且map是可能无限级联的。

```
import lombok.Data;

@Data
public class ErrorInfo {
    private String errorCode;
    private String errorMsg;
}
```



```

import org.apache.commons.lang3.StringUtils;

import java.util.Optional;


public class OptionalDemo {

    public static void main(String[] args) {
        Result result1 = null;
        System.out.println(getMsg(result1));
        System.out.println(getMsgByOptional(result1));


        System.out.println("----------");


        Result result2 = new Result();
        result2.setErrorInfo(null);
        System.out.println(getMsg(result2));
        System.out.println(getMsgByOptional(result2));


        System.out.println("----------");


        Result result3 = new Result();
        ErrorInfo result3ErrorInfo = new ErrorInfo();
        result3.setErrorInfo(result3ErrorInfo);
        System.out.println(getMsg(result3));
        System.out.println(getMsgByOptional(result3));


        System.out.println("----------");


        Result result4 = new Result();
        ErrorInfo result4ErrorInfo = new ErrorInfo();
        result4ErrorInfo.setErrorMsg("lala");
        result4.setErrorInfo(result4ErrorInfo);
        System.out.println(getMsg(result4));
        System.out.println(getMsgByOptional(result4));
    }

    private static String getMsg(Result result){
        if(result != null){
            ErrorInfo errorInfo = result.getErrorInfo();
            if(errorInfo!=null){
                String msg = errorInfo.getErrorMsg();
                return msg;
            }
        }
        return "";
    }

    private static String getMsgByOptional(Result result){
        return Optional.ofNullable(result)
                .map(Result::getErrorInfo)
                .map(ErrorInfo::getErrorMsg)
                .orElse(StringUtils.EMPTY);
    }
}
```

输出结果是：

```


----------


----------
null

----------
lala
lala
```

可以看到，第一组，都是输出的空字符串。

第二组，第一行是null，第二行是空字符串。

第三组，都是输出了“lala”。



