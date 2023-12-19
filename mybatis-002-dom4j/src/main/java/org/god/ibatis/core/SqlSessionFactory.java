package org.god.ibatis.core;

import org.god.ibatis.transaction.Transaction;
import org.god.ibatis.pojo.SqlMappedStatement;

import java.util.Map;
//对应godbatis-config.xml 文件
public class SqlSessionFactory {
    /**
     *     事务管理器
     *     为了达到可以随意切换事务管理器
     *     此处应该是面向接口编程
     *     所有的事务管理器都要遵循此规范
     */
    private Transaction transaction;

    //数据源
    /**
     *     此处的事务管理器是多余的,
     *     Transaction中的事务管理器是必须的
     *     当前类中存在 transaction对象
     *     可以通过Transaction获取数据源
     */


    //存放sql标签信息的Map集合
    private Map<String, SqlMappedStatement> mappedStatements;

    public SqlSessionFactory() {
    }

    public SqlSessionFactory(Transaction transaction, Map<String, SqlMappedStatement> mappedStatements) {
        this.transaction = transaction;
        this.mappedStatements = mappedStatements;
    }

    //开启会话
    public SqlSession openSession(){
        transaction.openConnection();
        SqlSession sqlSession = new SqlSession(this);
        return sqlSession;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public Map<String, SqlMappedStatement> getMappedStatements() {
        return mappedStatements;
    }

    public void setMappedStatements(Map<String, SqlMappedStatement> mappedStatements) {
        this.mappedStatements = mappedStatements;
    }
}
