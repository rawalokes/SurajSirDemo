package com.banking.dao;

import com.banking.model.Account;
import com.banking.model.Customer;
import com.banking.model.TransactedUser;
import com.banking.model.TransactionHistory;
import com.banking.utils.Utils;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import javax.persistence.EntityManager;


public class BankingDao {
    private static final SessionFactory factory;


    static {
        try {
            Configuration configuration =  new Configuration().configure();
            ServiceRegistry serviceregistry=new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
            factory = configuration.buildSessionFactory(serviceregistry);
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public Account addAccount(Customer customer,float interestRate){
        Session session = factory.openSession();
        Transaction tx = null;
        try {

            tx = session.beginTransaction();
            int customerId = (int) session.save(customer);
            Account account = new Account();
            customer.setId(customerId);
            account.setCustomer(customer);
            int accountId = (int) session.save(account);
            account.setAccountId(accountId);
            tx.commit();
            return account;
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    public boolean deposit(int accountNumber, TransactedUser user, double amount){
        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            Account account = session.get(Account.class, accountNumber);
            TransactionHistory history = new TransactionHistory();
            history.setAction(TransactionHistory.PayrollAction.DEPOSITED);
            history.setTransactionAmount(amount);
            history.setTransactionDate(System.currentTimeMillis());
            history.setUser(user);
            account.getTransactionHistories().add(history);
            account.setAmount(amount + calculateInterest(account.getAmount(),account.getLastTotalAmountCalculatedDate(),account.getInterestRate()));
            account.setLastTotalAmountCalculatedDate(System.currentTimeMillis());
            session.update(account);
            tx.commit();
            return true;
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return false;
    }
    
    private double calculateInterest(double amount, long time, float rate){
        int timeInDays = Utils.getDiffInDays(System.currentTimeMillis(),time);
        return  (amount * timeInDays * rate) / (100 * 365);
    }

    public double withDraw(int accountNumber, TransactedUser user, double amount){
        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            Account account = session.get(Account.class, accountNumber);
            TransactionHistory history = new TransactionHistory();
            history.setAction(TransactionHistory.PayrollAction.WITHDRAW);
            history.setTransactionAmount(amount);
            history.setTransactionDate(System.currentTimeMillis());
            history.setUser(user);
            account.getTransactionHistories().add(history);
            double totalAmount = account.getAmount() + calculateInterest(account.getAmount(),account.getLastTotalAmountCalculatedDate(),account.getInterestRate());
            if(totalAmount < amount){
                //throw exception
                return 0;
            }
            account.setAmount(totalAmount - amount);
            account.setLastTotalAmountCalculatedDate(System.currentTimeMillis());
            session.update(account);
            tx.commit();
            return amount;
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return 0;
    }

}
