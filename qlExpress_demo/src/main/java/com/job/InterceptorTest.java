package com.job;

import com.ql.util.express.DefaultContext;
import com.ql.util.express.ExpressRunner;
import com.ql.util.express.IExpressContext;
import com.ql.util.express.config.QLExpressTimer;

public class InterceptorTest {
    public static ExpressRunner runner = new ExpressRunner();

    public static void main(String[] args) throws Exception {
        Model model = new Model();
        model.setProperty("value-A");

        IExpressContext<String,Object> context = new DefaultContext<>();
        context.put("model", model);

        String condition = "if(\"value-A\".equals(model.getProperty())){\n" +
                "    return true;\n" +
                "}else{\n" +
                "    return false;\n" +
                "}";

        QLExpressTimer.setTimer(500);
        boolean isMatch = executeCondition(condition, context);
        if(isMatch){
            System.out.println("条件匹配，执行脚本");
            String express = "return \"INTERCEPT\";";
            Object result = executeActionScript(express, context);
            System.out.println(model);
            if(((String)result).startsWith("INTERCEPT")){
                throw new Exception("拦截");
            }
        }
    }

    public static boolean executeCondition(String expression, IExpressContext<String,Object> context) throws Exception {
        QLExpressTimer.setTimer(500);
        Object r = runner.execute(expression, context, null, true, true);
        QLExpressTimer.reset();
        if(r!=null && r instanceof Boolean){
            return (Boolean)r;
        }
        return false;
    }

    public static Object executeActionScript(String expression, IExpressContext<String,Object> context) throws Exception {
        QLExpressTimer.setTimer(500);
        Object r = runner.execute(expression, context, null, true, true);
        QLExpressTimer.reset();
        return r;
    }
}
