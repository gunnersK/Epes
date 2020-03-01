package com.gunners.epes.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.gunners.epes.entity.Employee;
import com.gunners.epes.service.IEmployeeService;
import com.gunners.epes.service.impl.EmployeeServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Objects;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestHutool {

    @Autowired
    IEmployeeService employeeService;

    @Test
    public void testDate(){
//        System.out.println(DateUtil.thisYear());
//        System.out.println(DateUtil.thisMonth());
//        System.out.println(DateUtil.today());
        List<String> date = StrUtil.split(DateUtil.today(), '-');
        String year = date.get(0).substring(2,4);
        String month = date.get(1);
        System.out.println(year+"="+month);
    }

    @Test
    public void testNum(){
//        Integer a = Integer.valueOf("001");
//        System.out.println(a);
        String order;
        String subId = "1012001";
        Employee lastEmp = employeeService.getLastEmp(subId);
        if(Objects.isNull(lastEmp)){
            order = subId + "01";
        } else{
            String lastId = lastEmp.getEmpId();
            lastId = lastId.substring(lastId.length() - 2, lastId.length());
            if(Integer.valueOf(lastId) < 9){
                Integer newId = Integer.valueOf(lastId) + 1;
                order = "0" + String.valueOf(newId);
            } else{
                Integer newId = Integer.valueOf(lastId) + 1;
                order = String.valueOf(newId);
            }
        }
        String empId = StrUtil.format("1{}{}{}{}", "02", "20", "03", order);
        System.out.println(empId);
    }
}
