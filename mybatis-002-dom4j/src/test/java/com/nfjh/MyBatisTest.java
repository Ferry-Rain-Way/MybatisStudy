package com.nfjh;

import com.pojo.User;
import org.god.ibatis.core.SqlSession;
import org.god.ibatis.core.SqlSessionFactory;
import org.god.ibatis.core.SqlSessionFactoryBuilder;
import org.god.ibatis.pojo.SqlMappedStatement;
import org.god.ibatis.util.Resources;
import org.junit.Test;

import java.util.Map;

public class MyBatisTest {

    @Test
    public void testGod(){
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        SqlSessionFactory factory = builder.build(Resources.getResourceAsStream("godbatis-config.xml"));

    }

    @Test
    public void testInsertUser(){
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        SqlSessionFactory factory = builder.build(Resources.getResourceAsStream("godbatis-config.xml"));
        SqlSession sqlSession = factory.openSession();
        User user = new User("111","zhangsan","21");

        sqlSession.insert("user.insertUser",user);
        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void testSelectOne(){
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        SqlSessionFactory factory = builder.build(Resources.getResourceAsStream("godbatis-config.xml"));
        SqlSession sqlSession = factory.openSession();
        Object o = sqlSession.selectOne("user.selectById", "111");
        System.out.println(o);
        sqlSession.close();
    }


}


