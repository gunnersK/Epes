package com.gunners.epes.utils;

import org.junit.Test;

import java.time.Instant;

public class TestTime {

    @Test
    public void aa(){
        System.out.println(Instant.now().getEpochSecond());
    }
}
