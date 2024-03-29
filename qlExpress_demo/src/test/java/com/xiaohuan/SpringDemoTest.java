package com.xiaohuan;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SpringDemoTest {
    @Autowired
    private QlExpressUtil qlExpressUtil;

    @Test
    public void testScript() throws Exception {
        String express = ""
                + "if(bizLogicBean.getUserInfo(nick)!=null) {"
                + "    userDO = bizLogicBean.getUserInfo(nick);"
                + "    if(userDO.salary>20000 && userDO.salary<20000) {"
                + "        System.out.println('高级客户:'+userDO.nick);"
                + "    } else if(userDO.salary>=200000) {"
                + "        System.out.println('vip客户:'+userDO.nick);"
                + "    } else {"
                + "        System.out.println('普通客户:'+userDO.nick);"
                + "    }"
                + "} else {"
                + "    System.out.println('用户信息不存在');"
                + "}";

        Map<String, Object> context = new HashMap<>();
        context.put("nick", "马总");
        qlExpressUtil.execute(express, context);
        context.put("nick", "小王");
        qlExpressUtil.execute(express, context);
        context.put("nick", "XXX");
        qlExpressUtil.execute(express, context);
    }

    @Test
    public void testScript2() throws Exception {
        String express = ""
                + "userDO = 读取用户信息(nick);"
                + "if(userDO != null) {"
                + "    if(判定用户是否vip)"
                + "        System.out.println('vip客户:' + nick);"
                + "} else {"
                + "    System.out.println('用户信息不存在，nick:' + nick);"
                + "}";

        Map<String, Object> context = new HashMap<>();
        context.put("nick", "马总");
        qlExpressUtil.execute(express, context);

        context.put("nick", "小王");
        qlExpressUtil.execute(express, context);

        context.put("nick", "XXX");
        qlExpressUtil.execute(express, context);
    }
}
