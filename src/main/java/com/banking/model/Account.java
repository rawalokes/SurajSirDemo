package com.banking.model;

import javax.persistence.*;
import java.util.List;

@Entity
@IdClass(Account.class)
public class Account {
    @Id
    private int accountId;
    private double amount;
    private float interestRate;
    private long lastTotalAmountCalculatedDate;
    @OneToOne
    private Customer customer;
    @OneToMany
    private List<TransactionHistory> transactionHistories;

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public float getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(float interestRate) {
        this.interestRate = interestRate;
    }

    public long getLastTotalAmountCalculatedDate() {
        return lastTotalAmountCalculatedDate;
    }

    public void setLastTotalAmountCalculatedDate(long lastTotalAmountCalculatedDate) {
        this.lastTotalAmountCalculatedDate = lastTotalAmountCalculatedDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<TransactionHistory> getTransactionHistories() {
        return transactionHistories;
    }

    public void setTransactionHistories(List<TransactionHistory> transactionHistories) {
        this.transactionHistories = transactionHistories;
    }
}
