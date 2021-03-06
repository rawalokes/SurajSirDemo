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
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;


public class BankingDao {
    private static final SessionFactory factory;


    static {
        try {
            //  factory = new Configuration().configure().buildSessionFactory();

//            Configuration configuration =  new Configuration().configure();
//            ServiceRegistry serviceregistry=new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
//            factory = configuration.buildSessionFactory(serviceregistry);

            StandardServiceRegistry ssr = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
            Metadata meta = new MetadataSources(ssr).getMetadataBuilder().build();
             factory = meta.getSessionFactoryBuilder().build();
        } catch (Throwable ex) {
            ex.printStackTrace();
            throw new ExceptionInInitializerError(ex);
        }
    }

    public Account addAccount(Customer customer,float interestRate){
        Session session = factory.openSession();
        Transaction tx = null;
        try {

            tx = session.beginTransaction();
            int customerId = (int) session.save(customer);
            customer.setId(customerId);
            Account account = new Account();
            account.setInterestRate(interestRate);
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
            history.setName(user.getName());
            history.setContactNumber(user.getContactNumber());
            List<TransactionHistory> historyList = account.getTransactionHistories();
            if(historyList == null){
                historyList = new ArrayList<>();
            }
            historyList.add(history);
            account.setTransactionHistories(historyList);
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
            history.setName(user.getName());
            history.setContactNumber(user.getContactNumber());
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
