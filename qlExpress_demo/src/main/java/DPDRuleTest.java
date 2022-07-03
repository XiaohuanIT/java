import com.ql.util.express.DefaultContext;
import com.ql.util.express.ExpressRunner;
import com.ql.util.express.IExpressContext;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class DPDRuleTest {
    @Test
    public void DPDRuleTest() throws Exception {
        List<String> ruleFileNames = new ArrayList<String>();
        ruleFileNames.add("rule/DPD_UK.ql");
        for (int i = 0; i < ruleFileNames.size(); i++) {
            String script = getResourceAsStream(ruleFileNames.get(i));
            ExpressRunner runner = new ExpressRunner(false, false);
            runner.addOperatorWithAlias("如果", "if", null);
            runner.addOperatorWithAlias("则", "then", null);
            runner.addOperatorWithAlias("否则", "else", null);
            IExpressContext<String, Object> context = new DefaultContext<String, Object>();
            try {
                context.put("长", 20);
                context.put("宽", 20);
                context.put("高", 20);
                context.put("重量", 10);
                context.put("COUNTRY","IS");
                runner.execute(script, context, null, true, false);
                if (String.valueOf(context.get("是否符合")).equals("1")) {
                    System.out.println("文件名称：" + ruleFileNames.get(i));
                    System.out.println("最长边：" + context.get("最长边"));
                    System.out.println("中间边：" + context.get("中间边"));
                    System.out.println("最短边：" + context.get("最短边"));
                    System.out.println("是否符合：" + context.get("是否符合"));
                    System.out.println("运费：" + context.get("运费"));                }
            } catch (Exception e) {
                e.printStackTrace();
                //Assert.assertTrue(e.toString().contains("at line 7"));
            }
        }
    }
    public static String getResourceAsStream(String path) throws Exception {
        InputStream in = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream(path);
        if (in == null) {
            throw new Exception("classLoader中找不到资源文件:" + path);
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
        StringBuilder builder = new StringBuilder();
        String tmpStr = null;
        while ((tmpStr = reader.readLine()) != null) {
            builder.append(tmpStr).append("\n");
        }
        reader.close();
        in.close();
        return builder.toString();
    }
}
