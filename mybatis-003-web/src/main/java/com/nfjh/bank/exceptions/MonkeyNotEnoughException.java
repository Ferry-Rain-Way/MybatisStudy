package com.nfjh.bank.exceptions;

/**
 * 余额不足异常
 */
public class MonkeyNotEnoughException extends Exception{
    public MonkeyNotEnoughException() {
    }

    public MonkeyNotEnoughException(String message) {
        super(message);
    }
}
