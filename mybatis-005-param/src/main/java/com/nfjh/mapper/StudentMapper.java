package com.nfjh.mapper;

import com.nfjh.pojo.Student;

import java.util.List;
import java.util.Map;

public interface StudentMapper {
    List<Student> selectById (Long id);

    int  insertStudentByMap(Map<String,Object> map);

    List<Student> selectByNameSex(String name,Character Sex);
}
