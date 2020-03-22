package com.gunners.epes.service.impl;

import cn.hutool.core.util.StrUtil;
import com.gunners.epes.constants.CommKeyConstants;
import com.gunners.epes.entity.*;
import com.gunners.epes.service.IGetCacheService;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetCacheServiceImpl implements IGetCacheService {

    @Autowired
    private RedissonClient redissonClient;

    @Override
    public EmpInfo getEmpInfo(String empId) {
        RMap<String, EmpInfo> map = redissonClient.getMap(CommKeyConstants.EMPINFO_KEY);
        String key = StrUtil.format("emp_info_id_{}", empId);
        return map.get(key);
    }

    @Override
    public Notice getNotice(Integer ntId) {
        RMap<String, Notice> map = redissonClient.getMap(CommKeyConstants.NOTICE_KEY);
        String key = StrUtil.format("nt_id_{}", ntId.toString());
        return map.get(key);
    }

    @Override
    public Project getProject(Integer prjId) {
        RMap<String, Project> map = redissonClient.getMap(CommKeyConstants.PROJECT_KEY);
        String key = StrUtil.format("prj_id_{}", prjId.toString());
        return map.get(key);
    }

    @Override
    public PrjTask getPrjTask(Integer taskId) {
        RMap<String, PrjTask> map = redissonClient.getMap(CommKeyConstants.PRJTASK_KEY);
        String key = StrUtil.format("task_id_{}", taskId.toString());
        return map.get(key);
    }

    @Override
    public TaskEvaInfo getTaskEvaInfo(Integer evaId) {
        RMap<String, TaskEvaInfo> map = redissonClient.getMap(CommKeyConstants.TASKEVA_INFO_KEY);
        String key = StrUtil.format("eva_id_{}", evaId.toString());
        return map.get(key);
    }
}
