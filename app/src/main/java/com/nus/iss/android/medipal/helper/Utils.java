package com.nus.iss.android.medipal.helper;

import com.nus.iss.android.medipal.constants.Constants;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by siddharth on 3/20/2017.
 */

public class Utils {

    public static Date increaseDateByGivenNumberOfMonths(Date date,int months){
        Calendar cal=Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH,months);
        Date newdate=cal.getTime();
        return newdate;
    }
    public static String convertDateToString(Date date){
        DateFormat format=new SimpleDateFormat(Constants.SIMPLE_DATE_FORMAT,Locale.getDefault());
        String dateString=format.format(date);
        return dateString;
    }

    public static Date converStringToDate(String dateString) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("EE MMM dd HH:mm:ss z yyyy",
                Locale.ENGLISH);

        Date date=sdf.parse(dateString);
        return date;
    }
    public static Date inceaseTimeByGivenNumberOfHours(Date date,int hour){
        Calendar cal=Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.HOUR_OF_DAY,hour);
        Date newdate=cal.getTime();
        return newdate;
    }
    public static String convertTimeToString(Date date){
        DateFormat format=new SimpleDateFormat(Constants.SIMPLE_TIME_FORMAT,Locale.getDefault());
        String dateString=format.format(date);
        return dateString;
    }
    public static Date converStringToTime(String dateString) throws ParseException {
        DateFormat format=new SimpleDateFormat(Constants.SIMPLE_TIME_FORMAT,Locale.getDefault());
        Date date=format.parse(dateString);
        return date;
    }

    public static String getTimeFromDateString(String dateString) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("EE MMM dd HH:mm:ss z yyyy",
                Locale.ENGLISH);
        Date date=sdf.parse(dateString);
        Calendar cal=Calendar.getInstance();
        cal.setTime(date);
        /*int hour=cal.get(Calendar.HOUR_OF_DAY);
        int min=cal.get(Calendar.MINUTE);
        String timeString=hour+ ":"+ min;*/
        Date newDate=cal.getTime();
        SimpleDateFormat format=new SimpleDateFormat(Constants.SIMPLE_TIME_FORMAT);
        String timeString=format.format(newDate);
        return timeString;
    }
}
