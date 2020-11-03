package com.gunners.epes;

import cn.hutool.http.HttpUtil;
import com.gunners.epes.controller.TestController;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


//@SpringBootTest
@Slf4j
class EpesApplicationTests {

    @Autowired
    TestController c;

    @Test
    void contextLoads() {
        String s = HttpUtil.get("https://static.msmds.cn/jplus/api/jdjtt/pull/goodsList");
        System.out.println(s);
    }

}
