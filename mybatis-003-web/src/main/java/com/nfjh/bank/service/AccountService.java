package com.nfjh.bank.service;

import com.nfjh.bank.exceptions.MonkeyNotEnoughException;
import com.nfjh.bank.exceptions.TransferException;

public interface AccountService {
    void transfer(String fromActno,String toActno,double monkey)
            throws MonkeyNotEnoughException, TransferException;
}
