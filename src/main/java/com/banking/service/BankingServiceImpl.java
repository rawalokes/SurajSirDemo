package com.banking.service;

import com.banking.dao.BankingDao;
import com.banking.model.Customer;
import com.banking.model.TransactedUser;

public class BankingServiceImpl implements BankingService{
    @Override
    public double withDraw(int accountNumber, double amount, TransactedUser user) {
       return new BankingDao().withDraw(accountNumber,user,amount);
    }

    @Override
    public boolean createAccount(Customer customer) {
       return false;
    }

    @Override
    public boolean deposit(int accountNumber, double amount, TransactedUser user) {
        return false;
    }
}
