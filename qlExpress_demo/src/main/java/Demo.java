
import com.ql.util.express.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class Demo {

    private ExpressRunner  runner = new ExpressRunner();

    // 如果性别为“男”，并且 分数 > 80分 那么此人授信500元  否则拒绝授信

    @Before
    public void init() throws Exception {
        //逻辑控制符号定义
        runner.addOperatorWithAlias("如果", "if",null);
        runner.addOperatorWithAlias("则", "then",null);
        runner.addOperatorWithAlias("否则", "else",null);
        runner.addOperatorWithAlias("并且", "and",null);
        runner.addOperatorWithAlias("或者", "or",null);

        //指标项定义
        runner.addFunctionOfClassMethod("getAge",Demo.class.getName(),"getAge",new String[] {User.class.getName()},null);
        runner.addMacro("年龄", "getAge(user)");

        runner.addFunctionOfClassMethod("getJob",Demo.class.getName(),"getJob",new String[] {User.class.getName()},null);
        runner.addMacro("工作", "getJob(user)");

        // 自定义操作符号   join
        class JoinOperator extends Operator {
            @Override
            public Object executeInner(Object[] list) throws Exception {
                Object opdata1 = list[0];
                Object opdata2 = list[1];
                if(opdata1 instanceof java.util.List){
                    ((java.util.List)opdata1).add(opdata2);
                    return opdata1;
                }else{
                    java.util.List result = new java.util.ArrayList();
                    result.add(opdata1);
                    result.add(opdata2);
                    return result;
                }
            }
        }
        runner.addOperator("join",new JoinOperator());

        // 自定义操作符  集合加
        class GroupOperator extends Operator {
            public GroupOperator(String aName) {
                this.name= aName;
            }
            @Override
            public Object executeInner(Object[] list)throws Exception {
                Object result = Integer.valueOf(0);
                for (int i = 0; i < list.length; i++) {
                    result = OperatorOfNumber.add(result, list[i],false);//根据list[i]类型（string,number等）做加法
                }
                return result;
            }
        }
        runner.addFunction("group", new GroupOperator("group"));
    }

    @Test
    public void testDemo5() throws  Exception{
        String express = "group(1,2,3)";
        System.out.println("表达式计算：" + express + " 处理结果： " + runner.execute(express, null, null, false, false) );
    }


    public int getAge(User user){
        return user.getAge();
    }

    public String getJob(User user){
        return user.getJob();
    }

    // +,-,*,/,<,>,<=,>=,==,!=,<>【等同于!=】,%,mod【取模等同于%】,
    @Test
    public void testDemo1() throws Exception{
        ExpressRunner runner = new ExpressRunner();

        String express1 =  "((1+2) >= 3) == false";
        System.out.println("表达式计算：" + express1 + " 处理结果： " + runner.execute(express1, null, null, false, false) );

        String express2 =  "(5 mod 2) != 7";
        System.out.println("表达式计算：" + express2 + " 处理结果： " + runner.execute(express2, null, null, false, false) );
    }

    //  and(&&) or(||)  !(非)  in【类似sql】  like【类似sql】
    @Test
    public  void testDemo2() throws Exception{
        ExpressRunner runner = new ExpressRunner();
        String express1 = "!(false and true)";
        System.out.println("表达式计算：" + express1 + " 处理结果： " + runner.execute(express1, null, null, false, false) );

        String express2 = "5 in(1,2,3)";
        System.out.println("表达式计算：" + express2 + " 处理结果： " + runner.execute(express2, null, null, false, false) );

        String express3 = "'abc' like 'ab%'";
        System.out.println("表达式计算：" + express3 + " 处理结果： " + runner.execute(express3, null, null, false, false) );

        String express4 = "2 in [1,2,3]";
        System.out.println("表达式计算：" + express4 + " 处理结果： " + runner.execute(express4, null, null, false, false) );

    }


    public void permission(User user, String express) throws Exception {
        IExpressContext<String,Object> expressContext = new DefaultContext<String,Object>();
        expressContext.put("user",user);
        int result = (Integer)runner.execute(express, expressContext, null, false, false);
        System.out.println("用户"+user.getName()+"授信："+result);
    }

    // if then else
    @Test
    public void testDemo3() throws Exception{
        Demo demo = new Demo();
        demo.init();

        List<String> ruleList = new ArrayList<String>();
        String express1 = "如果 年龄< 18 则 0 否则 { 如果 年龄 < 30 则 500 否则 {如果 年龄 < 50 则 1000 否则 100}}";
        User user =   new User("张三",35,"农民");
        demo.permission(user,express1);

        String express2 = "如果 (如果 1==2 则 false 否则 true) 则 {2+2} 否则 {20 + 20}";
        System.out.println("表达式计算：" + express2 + " 处理结果： " + runner.execute(express2, null, null, false, false) );
    }

    @Test
    public void testDemo4() throws Exception {
        String express = "1 join 2 join 3";
        Object r = runner.execute(express, null, null, false, false);
        System.out.println("表达式计算：" + express + " 处理结果： " + r );
    }

    @Test
    public void quick_start() throws Exception {
        ExpressRunner runner = new ExpressRunner();
        DefaultContext<String, Object> context = new DefaultContext<String, Object>();
        context.put("a", 1);
        context.put("b", 2);
        context.put("c", 3);
        //下面五个参数意义分别是 表达式，上下文，errorList，是否缓存，是否输出日志
        Object result = runner.execute("a+b+c", context, null, true, false);
        System.out.println("a+b+c=" + result);
    }

    @Test
    public void test_basic_use_for() throws Exception {
        ExpressRunner runner = new ExpressRunner();
        DefaultContext<String, Object> context = new DefaultContext<String, Object>();
        String express = "n=100;sum=0;" +
                "for(i=1;i<=n;i++){" +
                "sum = sum+i;" +
                "}" +
                "return sum;";
        Object result = runner.execute(express, context, null, true, false);
        System.out.println("1...100的和是: " + result);
    }

    @Test
    public void test_basic_use_three_var() throws Exception {
        ExpressRunner runner = new ExpressRunner();
        DefaultContext<String, Object> context = new DefaultContext<String, Object>();
        context.put("a", 5);
        context.put("b", 10);
        String express = "a>b?a:b";
        Object max = runner.execute(express, context, null, true, false);
        System.out.println("a和b中较大的指是：" + max);
    }

    @Test
    public void test_array_use() throws Exception {
        ExpressRunner runner = new ExpressRunner();
        DefaultContext<String, Object> context = new DefaultContext<String, Object>();
        String express = "arr=new int[3];" +
                "arr[0]=1;arr[1]=2;arr[2]=3;" +
                "sum=arr[0]+arr[1]+arr[2];" +
                "return sum;";
        Object arrSum = runner.execute(express, context, null, true, false);
        System.out.println("arrSum: " + arrSum);
    }

    @Test
    public void test_list_use() throws Exception {
        ExpressRunner runner = new ExpressRunner();
        DefaultContext<String, Object> context = new DefaultContext<String, Object>();
        String express = "list = new ArrayList();" +
                "list.add(3);list.add(4);list.add(5);" +
                "sum=0;" +
                "for(i=0;i<list.size();i++){" +
                "sum = sum+list.get(i);" +
                "}" +
                "return sum;";
        Object listSum = runner.execute(express, context, null, true, false);
        System.out.println("listSum: " + listSum);
    }

    @Test
    public void test_map_use() throws Exception {
        ExpressRunner runner = new ExpressRunner();
        DefaultContext<String, Object> context = new DefaultContext<String, Object>();
        String express = "map=new HashMap();" +
                "map.put('a',2);map.put('b',2);map.put('c',2);" +
                "sum=0;" +
                "keySet=map.keySet();" +
                "keyArray=keySet.toArray();" +
                "for(i=0;i<keyArray.length;i++){" +
                "sum=sum+map.get(keyArray[i]);" +
                "}" +
                "return sum;";
        Object mapValueSum = runner.execute(express, context, null, true, false);
        System.out.println("mapValueSum: " + mapValueSum);
    }

    @Test
    public void test_NewList() throws Exception {
        ExpressRunner runner = new ExpressRunner();
        DefaultContext<String, Object> context = new DefaultContext<String, Object>();
        String express = "abc=NewList(1,2,3);return abc.get(0)+abc.get(1)+abc.get(2);";
        Object listSum = runner.execute(express, context, null, true, false);
        System.out.println("listSum: " + listSum);
    }

    @Test
    public void test_NewMap() throws Exception {
        ExpressRunner runner = new ExpressRunner();
        DefaultContext<String, Object> context = new DefaultContext<String, Object>();
        String express = "abc=NewMap('a':1,'b':2,'c':3);return abc.get('a')+abc.get('b')+abc.get('c');";
        Object mapSum = runner.execute(express, context, null, true, false);
        System.out.println("mapSum: " + mapSum);
    }

    @Test
    public void test_java_bean() throws Exception {
        ExpressRunner runner = new ExpressRunner();
        DefaultContext<String, Object> context = new DefaultContext<String, Object>();
        //qlexpress只会引入java.util.*和java.lang.*
        String express = "import com.ql.util.express.Person;" +
                "person=new Person();" +
                "person.sayHello();" +
                "person.sayHelloStatic();";
        runner.execute(express, context, null, true, false);
    }

    @Test
    public void test_add_func() throws Exception {
        ExpressRunner runner = new ExpressRunner();
        DefaultContext<String, Object> context = new DefaultContext<String, Object>();
        String express = "function add(int a,int b){" +
                "return a+b;" +
                "};" +
                "a=2;b=2;" +
                "return add(a,b);";
        Object funcResult = runner.execute(express, context, null, true, false);
        System.out.println("add(a,b)=" + funcResult);
    }

    @Test
    public void test_replace_if_then_else() throws Exception {
        ExpressRunner runner = new ExpressRunner();
        DefaultContext<String, Object> context = new DefaultContext<String, Object>();
        runner.addOperatorWithAlias("如果", "if", null);
        runner.addOperatorWithAlias("则", "then", null);
        runner.addOperatorWithAlias("否则", "else", null);
        context.put("语文", 100);
        context.put("数学", 100);
        context.put("英语", 100);
        String express = "如果 ((语文+数学+英语)>270) 则 return 1;否则 return 0;";
        Object result = runner.execute(express, context, null, true, false);
        System.out.println(result);
    }









}

class User{
    private String name;
    private int age;
    private String job;

    public User(String name, int age, String job) {
        this.name = name;
        this.age = age;
        this.job = job;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }
}
