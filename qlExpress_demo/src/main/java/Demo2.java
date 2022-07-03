import com.ql.util.express.DefaultContext;
import com.ql.util.express.ExpressRunner;
import org.junit.Test;


/**
 * 如何使用Operator
 */

public class Demo2 {
    /**
     * addOperator
     * 返回结果 [1, 2, 3]
     */
    @Test
    public void test_add_operator() throws Exception {
        ExpressRunner runner = new ExpressRunner();
        DefaultContext<String, Object> context = new DefaultContext<String, Object>();
        String express = "1 join 2 join 3";
        runner.addOperator("join", new JoinOperator());
        Object result = runner.execute(express, context, null, true, false);
        System.out.println(result);
    }

    /**
     * replaceOperator
     * [1, 2, 3]
     */
    @Test
    public void test_replace_operator() throws Exception {
        ExpressRunner runner = new ExpressRunner();
        DefaultContext<String, Object> context = new DefaultContext<String, Object>();
        String express = "1 + 2 + 3";
        runner.replaceOperator("+", new JoinOperator());
        Object result = runner.execute(express, context, null, true, false);
        System.out.println(result);
    }


    /**
     * addFunction
     * [1, 2, 3]
     */
    @Test
    public void test_add_function() throws Exception {
        ExpressRunner runner = new ExpressRunner();
        DefaultContext<String, Object> context = new DefaultContext<String, Object>();
        String express = "join(1,2,3)";
        runner.addFunction("join", new JoinOperator());
        Object result = runner.execute(express, context, null, true, false);
        System.out.println(result);
    }


}
