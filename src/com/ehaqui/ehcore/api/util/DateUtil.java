package com.ehaqui.ehcore.api.util;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;


public class DateUtil
{
    public static int compareDataInDays(Date date1, Date date2, int days)
    {
        if(date2 == null)
        {
            GregorianCalendar gc = new GregorianCalendar();
            gc.setTime(new Date());
            gc.add(Calendar.DATE, days);
            date2 = gc.getTime();
        }
        
        return (int) Math.abs(TimeUnit.MILLISECONDS.toDays(date1.getTime() - date2.getTime()));
    }
    
    public static int compareDataInDays(Date date1, Date date2)
    {
        return (int) Math.abs(TimeUnit.MILLISECONDS.toDays(date1.getTime() - date2.getTime()));
    }
    
    public static int compareDataInDays(Date date)
    {
        return (int) Math.abs(TimeUnit.MILLISECONDS.toDays(date.getTime() - new Date().getTime()));
    }
    
    
}
