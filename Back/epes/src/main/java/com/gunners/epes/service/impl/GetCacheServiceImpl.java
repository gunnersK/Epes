package com.gunners.epes.service.impl;

import cn.hutool.core.util.StrUtil;
import com.gunners.epes.constants.CommKeyConstants;
import com.gunners.epes.entity.*;
import com.gunners.epes.service.IGetCacheService;
import org.redisson.api.RMap;
import org.redisson.api.RSet;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class GetCacheServiceImpl implements IGetCacheService {

    @Autowired
    private RedissonClient redissonClient;

    @Override
    public <T> T get(String rmapKey, String idKey){
        RMap<String, T> map = redissonClient.getMap(rmapKey);
        T entity = map.get(idKey);
        return entity;
    }

    @Override
    public Set getSet(String setKey) {
        Set set = redissonClient.getSet(setKey);
        return set;
    }

    @Override
    public Set<String> getAllKeys(String rmapKey) {
        RMap map = redissonClient.getMap(rmapKey);
        Set set = map.keySet();
        return set;
    }

    @Override
    public EmpInfo getEmpInfo(String empId) {
        String idKey = StrUtil.format("emp_info_id_{}", empId);
        return this.get(CommKeyConstants.EMPINFO_KEY, idKey);
    }

    @Override
    public Notice getNotice(Integer ntId) {
        String idKey = StrUtil.format("nt_id_{}", ntId.toString());
        return this.get(CommKeyConstants.NOTICE_KEY, idKey);
    }

    @Override
    public Notice getLastNotice() {
        return this.get(CommKeyConstants.LAST_NT, CommKeyConstants.LAST_NT);
    }

    @Override
    public Project getProject(Integer prjId) {
        String idKey = StrUtil.format("prj_id_{}", prjId.toString());
        return this.get(CommKeyConstants.PROJECT_KEY, idKey);
    }

    @Override
    public PrjTask getPrjTask(Integer taskId) {
        String idKey = StrUtil.format("task_id_{}", taskId.toString());
        return this.get(CommKeyConstants.PRJTASK_KEY, idKey);
    }

    @Override
    public TaskEvaInfo getTaskEvaInfo(Integer evaId) {
        String idKey = StrUtil.format("eva_id_{}", evaId.toString());
        return this.get(CommKeyConstants.TASKEVA_INFO_KEY, idKey);
    }

    @Override
    public DailyLog getDailyLog(Integer id) {
        String idKey = StrUtil.format("log_id_{}", id.toString());
        return this.get(CommKeyConstants.DAILY_LOG_KEY, idKey);
    }
}
