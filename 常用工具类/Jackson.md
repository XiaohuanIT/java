1、添加jackson库
```
<dependency>  
	<groupId>com.fasterxml.jackson.core</groupId>  
	<artifactId>jackson-databind</artifactId>  
	<version>2.8.3</version>  
</dependency>
```


用到jackson的类中需要引入：
```
import com.fasterxml.jackson.databind.ObjectMapper;  
import com.fasterxml.jackson.databind.DeserializationFeature;  
```


2、json转object

比如本例中是YourJson（json类型字符串）需要转化为YourClass类(自定义的类)的实例：
```
ObjectMapper objectMapper = new ObjectMapper();  
YourClass class = objectMapper.readValue(YourJson, YourClass.class);  
```
如果json中有新增的字段并且是YourClass类中不存在的，则会转换错误  
1）需要加上如下语句：
这种方法的好处是不用改变要转化的类，即本例的YourClass。（如果YourClass不是你维护的，或者不可修改的，可以用这个方法）
```
ObjectMapper objectMapper = new ObjectMapper();  
objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);  
YourClass class = objectMapper.readValue(YourJson, YourClass.class);  
```

2）jackson库还提供了注解方法，用在class级别上：  
```
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;  
@JsonIgnoreProperties(ignoreUnknown = true)  
public class YourClass {  
    ...  
}
```

3、object转json  
本例中是YourClass对象需要转化为json：
```
import com.fasterxml.jackson.databind.ObjectMapper;  
 
ObjectMapper objectMapper = new ObjectMapper();  
YourClass yourClass = new YourClass();  
String json = objectMapper.writeValueAsString(yourClass);
``` 
