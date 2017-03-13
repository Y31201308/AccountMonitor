package com.account.monitor.t.UT;

import com.account.monitor.domain.unify.TimeFormat;
import org.joda.time.DateTime;
import org.junit.Test;

public class EnumTest {

    @Test
    public void enumTest001(){
//        try{
//            AccountType.valueOf("NORmAL");
//        }catch (Exception e){
//            e.printStackTrace();
//            System.out.println(e.getMessage());
//        }

    }

    @Test
    public void dateTimeTest(){
        System.out.println(TimeFormat.parseTime("2016-12-13 07:05:57.960 +0000"));
        System.out.println(TimeFormat.parseTime("2015-12-11 00:00:00.960 +0800"));
        DateTime time = TimeFormat.parseTime("2015-12-11 00:00:00.960 +0800");
        System.out.println(TimeFormat.formatTime(time));
        System.out.println(new DateTime());
    }


    @Test
    public void stringTest(){
        String s = "sky123";
        System.out.println(s.toUpperCase());
    }

}
