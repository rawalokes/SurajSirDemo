package com.banking.model;

import javax.persistence.Entity;

@Entity
public class TransactionHistory {

   public int getTransactionId() {
      return transactionId;
   }

   public void setTransactionId(int transactionId) {
      this.transactionId = transactionId;
   }

   public double getTransactionAmount() {
      return transactionAmount;
   }

   public void setTransactionAmount(double transactionAmount) {
      this.transactionAmount = transactionAmount;
   }

   public long getTransactionDate() {
      return transactionDate;
   }

   public void setTransactionDate(long transactionDate) {
      this.transactionDate = transactionDate;
   }

   public PayrollAction getAction() {
      return action;
   }

   public void setAction(PayrollAction action) {
      this.action = action;
   }

   public TransactedUser getUser() {
      return user;
   }

   public void setUser(TransactedUser user) {
      this.user = user;
   }

   private int transactionId;
   private double transactionAmount;
   private long transactionDate;
   private PayrollAction action;
   private TransactedUser user;

   public enum PayrollAction{
       WITHDRAW,
       DEPOSITED
   }
}
