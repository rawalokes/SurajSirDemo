package com.banking;

import com.banking.dao.BankingDao;
import com.banking.model.Customer;
import com.banking.utils.ServiceFactory;

import static spark.Service.*;
import static spark.Spark.get;


public class EntryClass {
    public static void main(String[] args) {
       /* try{
            Class.forName(BankingDao.class.getName());
        }catch (ClassNotFoundException exception){

        }

        Customer customer = new Customer();
        customer.setName("Shyam");
        customer.setEmail("shyam@gmail.com");
        customer.setPhoneNumber("9845047555");
        ServiceFactory.getBankingService().createAccount(customer);*/

        get("/hello",(req,res) -> "hello world");

    }
}
