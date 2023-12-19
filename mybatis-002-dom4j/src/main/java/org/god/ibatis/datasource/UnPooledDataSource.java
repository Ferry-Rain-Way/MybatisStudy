package org.god.ibatis.datasource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 * 不使用数据库连接池
 * 每次都新建Connection对象
 * 所有的数据源都需要实现
 * import javax.sql.DataSource;
 * 这是JDK自带的
 * 是一种规范
 */

public class UnPooledDataSource extends GenericDataSource  {

    public UnPooledDataSource(String driver, String url, String username, String password) {
        //创建对象并注册驱动
        super(driver, url, username, password);
    }

    @Override
    public Connection getConnection() throws SQLException {
        //获取连接对象
        Connection connection = DriverManager.getConnection(url, username, password);
        return connection;
    }
}