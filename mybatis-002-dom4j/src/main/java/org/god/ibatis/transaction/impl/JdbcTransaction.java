package org.god.ibatis.transaction.impl;

import org.god.ibatis.datasource.GenericDataSource;
import org.god.ibatis.transaction.Transaction;

import java.sql.Connection;
import java.sql.SQLException;


public class JdbcTransaction implements Transaction {
    //数据源
    private GenericDataSource dataSource = null;
    //是否开启自动提交
    private boolean autoCommit;

    //连接对象的获取
    private Connection conn;

    //创建事务管理器
    public JdbcTransaction(GenericDataSource dataSource, boolean autoCommit) {
        this.dataSource = dataSource;
        this.autoCommit = autoCommit;
    }

    @Override
    public void commit() {
        try {
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void rollback() {
        try {
            conn.rollback();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 一个事务管理器对应一个sqlSession 对象
     * 所以同一个事务管理器调用事务管理器
     * 获取连接时只能返回同一个Connection对象
     *
     * 一个sqlSession有一个事务管理器
     * 所以下一次打开获取Connection对象时
     * 有可能打开不同的事务管理器
     * @return
     */
    public void openConnection(){
        if (conn == null) {
            try {
                conn = dataSource.getConnection();
                conn.setAutoCommit(autoCommit);
            } catch (SQLException e) {
                e.printStackTrace();
                }
            }
        }

    /**
     *      为了保证后期执行SQL语句时的connection对象和
     *     事务管理器中的数据库连接对象相同,需要提供一个getConnection方法
     *     给外部使用
     * @return
     */
    @Override
    public Connection getConnection() {
        return conn;
    }
}
