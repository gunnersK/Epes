package com.gunners.epes.service.impl;

import com.gunners.epes.entity.EmpInfo;
import com.gunners.epes.mapper.EmpInfoMapper;
import com.gunners.epes.service.IEmpInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmpInfoServiceImpl implements IEmpInfoService {

    @Autowired
    EmpInfoMapper empInfoMapper;

    @Override
    public EmpInfo getEmpInfo(String empId) {
        return empInfoMapper.getEmpInfo(empId);
    }
}
