import com.ql.util.express.DefaultContext;
import com.ql.util.express.ExpressRunner;
import org.junit.Test;

public class Demo7 {
    @Test
    public void test_condition() throws Exception {
        String express = "如果 ((投放页面 是 'H5' 并且  人群 是 '新人') " +
                "或者 商品ID 包含 [1,2,3]  ) " +
                "则 {return '符合XX活动条件';} 否则 {return '不符合条件';}";
        DefaultContext<String, Object> context = new DefaultContext<String, Object>();
        ExpressRunner runner = new ExpressRunner(false, false);
        runner.addOperatorWithAlias("如果", "if", null);
        runner.addOperatorWithAlias("则", "then", null);
        runner.addOperatorWithAlias("或者", "||", null);
        runner.addOperatorWithAlias("返回","return",null);
        runner.addOperatorWithAlias("并且","&&", null);
        runner.addOperatorWithAlias("是","==", null);
        runner.addOperatorWithAlias("包含","in", null);
        runner.addOperatorWithAlias("否则","else", null);


        context.put("投放页面", "H4");
        context.put("人群", "新人");
        context.put("消费门槛", 10);
        context.put("类目", "酒店");
        context.put("商品ID", 3);
        context.put("SKU", 123);

        Object r = runner.execute(express, context, null, false, false, null);
        System.out.println(r);
    }
}
