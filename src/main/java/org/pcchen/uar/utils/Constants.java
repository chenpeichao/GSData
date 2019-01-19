package org.pcchen.uar.utils;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 配置常量工具类(不包含清博配置常量)
 *
 * @author cpc
 * @create 2018-12-19 16:39
 **/
@Component
public class Constants {

    public static void main(String[] args) throws ParseException {
        Date startDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2018-12-11 15:23:32");
        Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse("2019-01-10 23:23:32");
        int i = Constants.daysOfTwo(startDate, endDate);
//
//        Calendar cal = Calendar.getInstance();
//        Date now = cal.getTime();
        System.out.println(i);
//        System.out.println();
//        Integer dayGap = 7;
//        Integer tmpDayGap = Constants.daysOfTwo2(startDate, endDate);;
//        dayGap = dayGap > tmpDayGap ? tmpDayGap : dayGap;
//        System.out.println(dayGap);
    }


    public static int daysOfTwo(Date fDate, Date oDate) {

        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(fDate);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(oDate);
        int day1 = cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);

        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if (year1 != year2)   //同一年
        {
            int timeDistance = 0;
            for (int i = year1; i < year2; i++) {
                if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0)    //闰年
                {
                    timeDistance += 366;
                } else    //不是闰年
                {
                    timeDistance += 365;
                }
            }

            return timeDistance + (day2 - day1);
        } else    //不同年
        {
            System.out.println("判断day2 - day1 : " + (day2 - day1));
            return day2 - day1;
        }

    }

    public static int daysOfTwo2(Date fDate, Date oDate) {

        int days = (int) ((oDate.getTime() - fDate.getTime()) / (1000 * 3600 * 24));
        return days;

    }
}
