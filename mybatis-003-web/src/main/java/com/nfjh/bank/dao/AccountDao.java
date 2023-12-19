package com.nfjh.bank.dao;

import com.nfjh.bank.pojo.Account;

public interface AccountDao {
    //Dao只负责CRDU
    Account selectByActno(String actno);

    int updateByActno(Account act);
}
