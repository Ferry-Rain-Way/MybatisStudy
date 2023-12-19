package org.god.ibatis.datasource;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * JNDI 使用第三方的数据库连接池获取Connection对象
 * 所有的数据源都需要实现
 * import javax.sql.DataSource;
 * 这是JDK自带的
 * 是一种规范
 */


public class JNDIDataSource  extends GenericDataSource {


    public JNDIDataSource(String driver, String url, String username, String password) {
        //创建对象并注册驱动
        super(driver, url, username, password);
    }

    @Override
    public Connection getConnection() throws SQLException {

        return null;
    }
}
