package com.banking.utils;

import java.util.concurrent.TimeUnit;

public class Utils {
    public static int getDiffInDays(long greaterTime, long smallerTime){
        final long MILLIS_PER_DAY = 1000*60*60*24;
        greaterTime -= greaterTime % MILLIS_PER_DAY;
        smallerTime -= smallerTime % MILLIS_PER_DAY;
        return (int) TimeUnit.DAYS.convert(greaterTime - smallerTime , TimeUnit.MILLISECONDS);
    }
}
