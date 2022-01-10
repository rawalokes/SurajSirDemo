package com.banking.service;

import com.banking.dao.BankingDao;
import com.banking.model.Account;
import com.banking.model.Customer;
import com.banking.model.TransactedUser;

public class BankingServiceImpl implements BankingService{
    @Override
    public double withDraw(int accountNumber, double amount, TransactedUser user) {
       return new BankingDao().withDraw(accountNumber,user,amount);
    }

    @Override
    public boolean createAccount(Customer customer) {
        Account account = new BankingDao().addAccount(customer,5);
        return account != null;
    }

    @Override
    public boolean deposit(int accountNumber, double amount, TransactedUser user) {
        return false;
    }
}
