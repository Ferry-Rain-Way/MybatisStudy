package com.ts.mapper;



import com.ts.pojo.Student;

import java.util.List;

public interface StudentMapper {
//多对一------------------------------
//    根据学生的学号查找学生信息,包括班级信息
    Student  selectByStudentId(Integer id);

    // 使用association关联映射
    Student selectByStudentIdAssociation(Integer id);

    //使用分步查询step1
    //通过学生ID查询学生信息
    Student selectByStudentStep1(Integer id);

//一对多------------------------------
    List<Student> selectStudentStept2(Integer cid);

}
