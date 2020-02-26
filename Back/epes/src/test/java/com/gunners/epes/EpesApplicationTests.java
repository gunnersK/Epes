package com.gunners.epes;

import com.gunners.epes.controller.TestController;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
@Slf4j
class EpesApplicationTests {

    @Autowired
    TestController c;

    @Test
    void contextLoads() {
        String a = "d";
        System.out.println(a.getClass());
    }

}
