package com.xiaohuan;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.InputStream;

@Component
public class TestMybatis implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession session = sqlSessionFactory.openSession();
        try {
            /*UserMapper mapper = session.getMapper(UserMapper.class);
            User user = mapper.selectUserById(1);
            System.out.println(user);*/
            Object o = session.selectOne("com.xiaohuan.UserMapper.selectUserById", 1);
            System.out.println("我是第一次查询的"+o);
            System.out.println("-------------------------------我是分割线---------------------");
            Object z = session.selectOne("com.xiaohuan.UserMapper.selectUserById", 1);
            System.out.println("我是第二次查询的"+z);
           /*User user = new User();
           user.setAge(15);
           user.setName("achuan");
           int insert = session.insert("com.wsdsg.spring.boot.analyze.mapper.UserMapper.addOneUser", user);
           session.commit();
           System.out.println(insert);*/
        } finally {
            session.close();
        }
    }
}
