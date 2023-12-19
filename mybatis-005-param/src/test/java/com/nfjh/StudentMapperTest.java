package com.nfjh;

import com.nfjh.mapper.StudentMapper;
import com.nfjh.pojo.Student;
import com.nfjh.util.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentMapperTest {
    private SqlSession sql = null;
    @Before
    public void open(){
        sql = SqlSessionUtil.openSession();
    }
    @After
    public void close(){
        //存在事务处理时,此代码通用
        //    private static ThreadLocal<SqlSession> local = new ThreadLocal<>();
        SqlSessionUtil.close(sql);
    }


    @Test
    public void TestSelectById(){
        StudentMapper mapper = sql.getMapper(StudentMapper.class);
        List<Student> students = mapper.selectById(1L);
        students.forEach(stu -> System.out.println("stu ->" + stu));
    }
    @Test
    public void TestInsertStudentByMap(){
        StudentMapper mapper = sql.getMapper(StudentMapper.class);
       Map<String ,Object> map = new HashMap<>();
       map.put("ID",7L);
       map.put("姓名","");
       map.put("年龄",22L);
       map.put("身高",1.86D);
       map.put("生日",new Date());
       map.put("性别",'男');
        mapper.insertStudentByMap(map);
        sql.commit();
    }


}
