package com.nfjh.bank.service.impl;
import com.nfjh.bank.dao.AccountDao;
import com.nfjh.bank.exceptions.MonkeyNotEnoughException;
import com.nfjh.bank.exceptions.TransferException;
import com.nfjh.bank.pojo.Account;
import com.nfjh.bank.service.AccountService;
import com.nfjh.bank.utils.MyGenerateDaoProxy;
import com.nfjh.bank.utils.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;
public class AccountServiceImpl implements AccountService {
//    private AccountDao actDao = new AccuntDaoImpl();

// GenerateDaoProxy.generate(SqlSessionUtil.openSession(),AccountDao.class

//private AccountDao actDao = (AccountDao) MyGenerateDaoProxy.generate(
//    SqlSessionUtil.openSession(),
//    AccountDao.class,
//    "com.nfjh.bank.utils.SqlSessionUtil"
//);

    private AccountDao actDao = SqlSessionUtil.openSession().getMapper(AccountDao.class);

    @Override
    public void transfer(String fromActno, String toActno, double monkey)
            throws MonkeyNotEnoughException, TransferException {
        // 添加事务控制代码
        SqlSession sqlSession = SqlSessionUtil.openSession();
        //查询转出账户的余额
        Account fromActObj = actDao.selectByActno(fromActno);
        if (fromActObj.getBalance()< monkey)  {
                throw new MonkeyNotEnoughException("余额不足");
        }

        //使用set方法更新内存中的java对象数据
        fromActObj.setBalance(fromActObj.getBalance() - monkey);

        //查询转入账户信息并更新 内存中java对象余额
        Account toActObj = actDao.selectByActno(toActno);
        toActObj.setBalance(toActObj.getBalance() + monkey);

        /*模拟异常抛出*/
//        String s = null;
//        s.toString();

        //更新数据库
        int count= actDao.updateByActno(fromActObj);
        count+= actDao.updateByActno(toActObj);

        //更新失败异常抛出

        if (count != 2) {
            throw new TransferException("转账失败");
        }

        // 提交事务
        sqlSession.commit();
        // 关闭事务
        SqlSessionUtil.close(sqlSession);

    }
}
