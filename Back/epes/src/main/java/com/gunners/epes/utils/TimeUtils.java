package com.gunners.epes.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtils {

    /**
     * 通过年字符串获取时间戳秒数
     * @param format
     * @return
     * @throws Exception
     */
    public static Long getSecondByYear(String format){
        Long second = 0L;
        try{
            Date parse = new SimpleDateFormat("yyyy-MM-dd").parse(format);
            second = parse.getTime() / 1000;
            return second;
        } catch (Exception e){
            e.printStackTrace();
        }
        return second;
    }
}
