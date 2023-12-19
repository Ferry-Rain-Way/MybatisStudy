package org.god.ibatis.datasource;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

/**
 * javaWeb部分的笔记:
 * - 我们编写一个Servlet类直接实现Servlet接口有什么缺点？
 *   - 我们只需要service方法，其他方法大部分情况下是不需要使用的。代码很丑陋。
 * - 适配器设计模式Adapter
 *
 * 我在这里使用了这种方法
 * 注意该类是抽象的
 */
abstract public  class GenericDataSource implements DataSource {
    /**
     * 对于每一种创建Connection的方式
     * 都需要这些信息
     * driver url username password
     */
    public String driver = null;
    public String url = null;
    public String username = null;
    public String password = null;

    public GenericDataSource(String driver, String url, String username, String password) {
        this.driver = driver;
        this.url = url;
        this.username = username;
        this.password = password;

        //直接注册驱动
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
    //  Connection getConnection() throws SQLException;
    //这个方法不会由GenericDataSource实现,交给GenericDataSource
    //的子类去实现该方法


    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return null;
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return null;
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {

    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {

    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return 0;
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return null;
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;
    }
}
