package com.banking.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.servlet.http.HttpServlet;

@Entity
public class TransactionHistory {


   @Id
   private int transactionId;
   private double transactionAmount;
   private long transactionDate;
   private PayrollAction action;
   private String name;
   private String contactNumber;

   public enum PayrollAction{
      WITHDRAW,
      DEPOSITED
   }

   public String getName() {
      HttpServlet
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getContactNumber() {
      return contactNumber;
   }

   public void setContactNumber(String contactNumber) {
      this.contactNumber = contactNumber;
   }

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


}
