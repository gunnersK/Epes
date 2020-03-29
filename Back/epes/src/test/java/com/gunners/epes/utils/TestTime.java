package com.gunners.epes.utils;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

public class TestTime {

    @Test
    public void aa(){
        System.out.println(Instant.now().getEpochSecond());
    }

    @Test
    public void bb() throws Exception{
        String time = "2020-12-1";
        Date parse = new SimpleDateFormat("yyyy-MM-dd").parse(time);
        Long time1 = parse.getTime() / 1000;
        System.out.println(time + " >> " + time1);
    }
}
