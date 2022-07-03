import com.ql.util.express.DefaultContext;
import com.ql.util.express.ExpressRunner;
import org.junit.Test;

public class Demo6 {
    @Test
    public void test_discount() throws Exception {
        String express = "如果 (价格 大于 300)  则 {返回 价格*0.8;}  " +
                "如果 (价格 大于 200)  则 {返回 价格*0.85;}  " +
                "如果 (价格 大于 100 ) 则 {返回 价格*0.9;} " +
                "否则 {返回 价格;}";
        DefaultContext<String, Object> context = new DefaultContext<String, Object>();
        ExpressRunner runner = new ExpressRunner(false, false);
        runner.addOperatorWithAlias("如果", "if", null);
        runner.addOperatorWithAlias("则", "then", null);
        runner.addOperatorWithAlias("否则", "else", null);
        runner.addOperatorWithAlias("返回","return",null);
        runner.addOperatorWithAlias("大于",">", null);

        context.put("价格", 150);
        Object r = runner.execute(express, context, null, false, false, null);
        System.out.println(r);


        context.put("价格", 100);
        System.out.println(runner.execute(express, context, null, false, false, null));

        context.put("价格", 50);
        System.out.println(runner.execute(express, context, null, false, false, null));

    }
}
