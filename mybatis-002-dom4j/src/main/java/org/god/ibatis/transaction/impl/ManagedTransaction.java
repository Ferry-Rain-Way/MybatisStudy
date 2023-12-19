package org.god.ibatis.transaction.impl;

import org.god.ibatis.transaction.Transaction;

import java.sql.Connection;

/**
 * 这个事务管理器就不在实现了
 * 注重框架的核心
 */
public class ManagedTransaction implements Transaction {
    @Override
    public void commit() {

    }

    @Override
    public void rollback() {

    }

    @Override
    public void close() {

    }

    @Override
    public void openConnection() {

    }

    @Override
    public Connection getConnection() {
        return null;
    }
}
