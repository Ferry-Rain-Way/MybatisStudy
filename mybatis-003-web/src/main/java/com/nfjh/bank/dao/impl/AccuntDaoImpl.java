package com.nfjh.bank.dao.impl;

import com.nfjh.bank.dao.AccountDao;
import com.nfjh.bank.pojo.Account;
import com.nfjh.bank.utils.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;

/**
 * 当使用工具类后
 * AccuntDaoImpl这个实现类就不需要在手动写了
 * 可以删除
 */
public class AccuntDaoImpl implements AccountDao {

    /**
     * 查询语句事务的提交在方法中完成即可
     * 但sqlSession 对象的关闭不能在这人里
     * @param actno
     * @return
     */
    @Override
    public Account selectByActno(String actno) {
        //
        SqlSession sqlSession = SqlSessionUtil.openSession();
        Account  account  = (Account ) sqlSession.selectOne("accuntspace.selectByActno", actno);
    //  sqlSession.close();  // 不能再这里关闭,否则Connection。。。。

        return account;
    }

    @Override
    public int updateByActno(Account act) {
        SqlSession sqlSession = SqlSessionUtil.openSession();
        int count = sqlSession.update("accuntspace.updateByActno",act);
        sqlSession.commit();
        return count;
    }
}
