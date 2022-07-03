import com.ql.util.express.ExpressRunner;
import org.junit.Test;

public class Demo5 {

    /**
     * //输出结果：
     * var : 数学
     * var : 综合考试
     * var : 英语
     * var : 语文
     */
    @Test
    public void test_compile_script() throws Exception {
        String express = "int 平均分 = (语文 + 数学 + 英语 + 综合考试.科目2) / 4.0; return 平均分";
        ExpressRunner runner = new ExpressRunner(true, true);
        //获取一个表达式需要的外部变量名称列表
        String[] names = runner.getOutVarNames(express);
        for(String s:names){
            System.out.println("var : " + s);
        }
    }
}
