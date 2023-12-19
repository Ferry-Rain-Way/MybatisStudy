package org.god.ibatis.transaction;

import java.sql.Connection;

public interface Transaction {
    /**
     *     事务管理器
     *     为了达到可以随意切换事务管理器
     *     此处应该是面向接口编程
     *     所有的事务管理器都要遵循此规范
     */


    //提供事务管理的方法
    void commit();
    void rollback();
    void close();

    //正真的开启数据库连接
    void openConnection();

    Connection getConnection();
}
