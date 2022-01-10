package com.banking.utils;

import com.banking.service.BankingService;
import com.banking.service.BankingServiceImpl;

public class ServiceFactory {
    public static BankingService getBankingService(){
        return new BankingServiceImpl();
    }
}
