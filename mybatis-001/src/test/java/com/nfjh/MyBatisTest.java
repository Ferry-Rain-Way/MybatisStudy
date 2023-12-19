package com.nfjh;

import com.nfjh.util.SqlSessionUtil;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

public class MyBatisTest {
    @Test
    public void  tesdt1() throws IOException {
        InputStream is = Resources.getResourceAsStream("mybatis-config.xml");

        SqlSessionFactory ssf = new SqlSessionFactoryBuilder().build(is);

        SqlSession sqlSession = ssf.openSession();

        int insertCar = sqlSession.insert("insertCar");
        System.out.println(insertCar);

        sqlSession.commit();



    }


    @Test
    public void test2(){
        SqlSession sqlSession = SqlSessionUtil.openSqlSession();
        int insertCar = sqlSession.insert("insertCar");
        System.out.println(insertCar);
        sqlSession.commit();
        sqlSession.close();
    }

    SqlSession sqlSession;
    @Before
    public void  open(){
         sqlSession = SqlSessionUtil.openSqlSession();
    }
    @After
    public void  commit(){
        sqlSession.commit();
    }
    @Test
    public void testDelete(){
        sqlSession.delete("deleteByID",179);

    }

    @Test
    public void testSelect(){
        SqlSession sqlSession = SqlSessionUtil.openSqlSession();
        Object selectById = sqlSession.selectOne("selectById", 178);
        System.out.println(selectById);
        sqlSession.commit();
        sqlSession.close();
    }


    @Test
    public void  testConfig(){
        InputStream resourceAsStream = null;
        try {
            resourceAsStream = Resources.getResourceAsStream("mybatis-config.xml");
            SqlSessionFactory build = new SqlSessionFactoryBuilder().build(resourceAsStream,"du_mybatis");
            SqlSession sqlSession = build.openSession();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}



