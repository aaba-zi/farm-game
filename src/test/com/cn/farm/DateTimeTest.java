package com.cn.farm;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * DateTimeTest
 **/

public class DateTimeTest {

    @Test
    public void stringToDatetime(){
        Date date = DateUtil.parse("0000/0/2 2:00:00");
        System.out.println(date);
        date = DateUtil.date(Long.parseLong("1589698482219"));
        System.out.println(date);
    }

    @Test
    public void dateToString(){
        System.out.println(DateUtil.date().toString());
    }

    @Test
    public void dateBewteen(){
        Date startdate = DateUtil.parseDate("2020-05-19 11:01:36");
        Date nowDate = new Date();
        System.out.println(DateUtil.between(startdate, nowDate, DateUnit.HOUR));
        System.out.println(DateUtil.offset(startdate, DateField.HOUR, 2));
    }

    @Test
    public void calendarTest(){
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
}
