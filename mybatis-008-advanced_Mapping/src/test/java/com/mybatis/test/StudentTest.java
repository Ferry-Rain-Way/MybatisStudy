package com.mybatis.test;

import com.ts.mapper.StudentMapper;
import com.ts.pojo.Student;
import com.ts.util.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import javax.swing.text.StyleContext;

public class StudentTest {
    @Test
    public void selectByStudentIdTest(){
        SqlSession sqlSession = SqlSessionUtil.openSession();
        StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
        Student student = mapper.selectByStudentId(1);
        System.out.println(student);

        /*
        * org.apache.ibatis.binding.BindingException:
        *  Invalid bound statement (not found):
        * com.ts.mapper.StudentMapper.selectByStudentId
        * 绑定异常
        * 查看XML文件的存放位置是否正确
        * 查看核心配置文件中的 mapper绑定等
        * */
    }

    @Test
    public void selectByStudentIdAssociationTest(){
        SqlSession sqlSession = SqlSessionUtil.openSession();
        StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
        Student student = mapper.selectByStudentIdAssociation(1);
        System.out.println("association =>" + student);

        /*
         * org.apache.ibatis.binding.BindingException:
         *  Invalid bound statement (not found):
         * com.ts.mapper.StudentMapper.selectByStudentId
         * 绑定异常
         * 查看XML文件的存放位置是否正确
         * 查看核心配置文件中的 mapper绑定等
         * */


    }

    @Test
    public void selectByStep(){
        SqlSession sqlSession = SqlSessionUtil.openSession();
        StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
        Student student = mapper.selectByStudentStep1(1);
        System.out.println(student.getSname());
        sqlSession.close();
        /*未开启懒加载时在查询的过程中只需要调用Step1即可,Step2会自动被调用
        * 即执行了两条SQL语句
        * Preparing: select sid,sname,cid from t_stu where sid =?
        * Preparing: select cid,cname from t_clazz where cid = ?
        *
        * 当使用懒加载后发现对于以上相同的代码片段
        * SQL语句只会执行一次 Preparing: select sid,sname,cid from t_stu where sid =?
        * */
    }

}
