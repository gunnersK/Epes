package com.gunners.epes.utils;

import sun.nio.ch.ThreadPool;

import java.util.concurrent.*;

public class TestThreadPool {

    public void aa(){
        ExecutorService cache = Executors.newCachedThreadPool();
        ExecutorService fix = Executors.newFixedThreadPool(5);
    }
}
