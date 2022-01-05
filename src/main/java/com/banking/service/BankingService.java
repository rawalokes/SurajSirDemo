package com.banking.service;

import com.banking.model.Customer;
import com.banking.model.TransactedUser;

public interface BankingService {

    double withDraw(int accountNumber, double amount, TransactedUser user);

    boolean createAccount(Customer customer);

    boolean deposit(int accountNumber, double amount, TransactedUser user);
}
