package org.god.ibatis.datasource;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 使用数据库连接池
 * 优先从数据库连接池中获取Connection对象
 * 可以自己写一个数据库连接池,当然这里就不写了
 * 所有的数据源都需要实现
 * import javax.sql.DataSource;
 * 这是JDK自带的
 * 是一种规范
 */
public class PooledDataSource   extends  GenericDataSource{

    public PooledDataSource(String driver, String url, String username, String password) {
        //创建对象并注册驱动
        super(driver, url, username, password);
    }

    @Override
    public Connection getConnection() throws SQLException {
        return null;
    }
}