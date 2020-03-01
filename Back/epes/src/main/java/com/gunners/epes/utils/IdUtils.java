package com.gunners.epes.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.gunners.epes.entity.Employee;
import com.gunners.epes.service.IEmployeeService;
import com.gunners.epes.service.impl.EmployeeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class IdUtils {

    @Autowired
    private IEmployeeService employeeService;

    @Autowired
    RedissonUtils redissonUtils;

    /**
     * 生成员工id
     * @return
     */
    public String generateEmpId(){
        //部门id
        String dpartId = redissonUtils.getTransmitId("admin_id", "dpart_id");
        if(Integer.valueOf(dpartId) < 10){
            dpartId = 0 + dpartId;
        }

        //日期
        List<String> date = StrUtil.split(DateUtil.today(), '-');
        String year = date.get(0).substring(2,4);
        String month = date.get(1);

        //编号顺序
        String order;
        String subId = "1" + dpartId + year + month;
        Employee lastEmp = employeeService.getLastEmp(subId);
        if(Objects.isNull(lastEmp)){
            order = "01";
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

        //生成新员工id
        String empId = StrUtil.format("1{}{}{}{}", dpartId, year, month, order);
        return empId;
    }
}
