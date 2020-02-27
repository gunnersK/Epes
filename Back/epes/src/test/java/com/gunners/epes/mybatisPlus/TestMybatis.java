package com.gunners.epes.mybatisPlus;

import com.gunners.epes.entity.Employee;
import com.gunners.epes.entity.User;
import com.gunners.epes.mapper.UserMapper;
import com.gunners.epes.service.IDailyLogService;
import com.gunners.epes.service.IEmployeeService;
import com.gunners.epes.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@Slf4j
@SpringBootTest
public class TestMybatis {

    @Autowired
    UserMapper userMapper;

    @Autowired
    IUserService iUserService;

    @Autowired
    IEmployeeService iEmployeeService;

    @Autowired
    IDailyLogService iDailyLogService;

    @Test
    void test(){
        log.info("----- selectAll method test ------");
//        log.info("="+iDailyLogService.getById(1));
//        List<User> list = userMapper.selectList(null);
        List<Employee> list = iEmployeeService.list();
        list.forEach(System.out::println);
    }

    @Test
    void testSplit(){
        String[] ss = "".trim().split(",");
        System.out.println(ss[0]);
    }

}
