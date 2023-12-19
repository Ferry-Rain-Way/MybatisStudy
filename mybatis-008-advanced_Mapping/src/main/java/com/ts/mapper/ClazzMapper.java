package com.ts.mapper;


import com.ts.pojo.Clazz;


public interface ClazzMapper {

    //多对一
    Clazz selectByClazzStep2(Integer cid);


    //一对多根据班级CID查询班内学生
    Clazz  selectByCollection(Integer cid);

    //一对多分步查询Step1 查询 通过cid 查找班级信息[cid,cname]
    Clazz selectByStep1(Integer cid);

}
