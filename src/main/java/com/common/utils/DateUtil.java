package com.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by ccc016 on 2016/6/15.
 */
public class DateUtil {
    public static final String THREE_YEAR_AGE="2016-01-01";
    private DateUtil(){ }

    public static String GET_CURRENT_TIME(){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return sdf.format(new Date());
    }

    public static String GET_CURRENT_DATE(){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date());
    }

    public static String GET_DATE_AFTER_ONE_DAY(String dateStr){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date=sdf.parse(dateStr);
            return sdf.format(date.getTime()+60*60*24*1000);
        } catch (ParseException e) {

            e.printStackTrace();
        }
        return null;
    }





}
