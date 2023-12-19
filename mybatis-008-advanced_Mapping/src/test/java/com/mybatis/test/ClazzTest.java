package com.mybatis.test;

import com.ts.mapper.ClazzMapper;
import com.ts.pojo.Clazz;
import com.ts.util.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;



public class ClazzTest {
    @Test
    public void  selectByCollectionTest(){
        SqlSession sqlSession = SqlSessionUtil.openSession();
        ClazzMapper mapper = sqlSession.getMapper(ClazzMapper.class);
        Clazz clazz = mapper.selectByCollection(1000);
        System.out.println(clazz);
        sqlSession.close();

    }

    @Test
    public void selectStudentSteptTest(){
        SqlSession sqlSession = SqlSessionUtil.openSession();
        ClazzMapper mapper = sqlSession.getMapper(ClazzMapper.class);
        Clazz clazz = mapper.selectByStep1(1000);
//        System.out.println(clazz);
        System.out.println(clazz.getCname());
        sqlSession.close();

    }

}
