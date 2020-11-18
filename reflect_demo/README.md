"Settings > Build > Compiler > Annotation Processors"


JAVA反射机制是在运行状态中，对于任意一个类，都能够知道这个类的所有属性和方法；对于任意一个对象，都能够调用它的任意方法和属性；这种动态获取信息以及动态调用对象方法的功能称为java语言的反射机制。

getField和getDeclaredField都是Class类的方法，反射成员变量时使用(getMethod和getDeclaredMethod类似)。

getField: 获取一个类的 == public成员变量，包括基类== 。
getDeclaredField：获取一个类的 ==所有成员变量，不包括基类== 。

Field.setAccessible：成员变量为private，必须进行此操作。



Guava之CaseFormat
com.google.common.base.CaseFormat是一种实用工具类，以提供不同的ASCII字符格式之间的转换。

枚举常量

| S.N.       | 枚举常量和说明         | 
|:-----------:| -------------:|
| 1 | LOWER_CAMEL    Java变量的命名规则，如“lowerCamel”。  | 
| 2 | LOWER_HYPHEN    连字符连接变量的命名规则，如“lower-hyphen”。 |
| 3 | LOWER_UNDERSCORE    C ++变量命名规则，如“lower_underscore”。|
| 4 | UPPER_CAMEL    Java和C++类的命名规则，如“UpperCamel”。|
| 5 | UPPER_UNDERSCORE    Java和C++常量的命名规则，如“UPPER_UNDERSCORE”。|