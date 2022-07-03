import com.ql.util.express.DefaultContext;
import com.ql.util.express.ExpressRunner;
import org.junit.Test;

/**
 * 绑定java类或者对象的method
 * addFunctionOfClassMethod + addFunctionOfServiceMethod
 */

public class Demo3 {

    @Test
    public void test_add_funciton() throws Exception {
        ExpressRunner runner = new ExpressRunner();
        DefaultContext<String, Object> context = new DefaultContext<String, Object>();

        runner.addFunctionOfClassMethod("取绝对值", Math.class.getName(), "abs", new String[] {"double"}, null);
        runner.addFunctionOfClassMethod("转换为大写", BeanExample.class.getName(), "upper", new String[] {"String"}, null);

        runner.addFunctionOfServiceMethod("打印", System.out, "println", new String[] { "String" }, null);
        runner.addFunctionOfServiceMethod("contains", new BeanExample(), "anyContains", new Class[] {String.class, String.class}, null);

        String express = "取绝对值(-100); 转换为大写(\"hello world\"); 打印(\"你好吗？\"); contains(\"helloworld\",\"aeiou\")";
        Object result = runner.execute(express, context, null, false, false);
        System.out.println(result);
    }
}
