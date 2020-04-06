package com.gunners.epes.service.impl;

import cn.hutool.core.util.StrUtil;
import com.gunners.epes.constants.CommKeyConstants;
import com.gunners.epes.entity.EmpInfo;
import com.gunners.epes.entity.Notice;
import com.gunners.epes.entity.PrjTask;
import com.gunners.epes.entity.Project;
import com.gunners.epes.service.IClearCacheService;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClearCacheServiceImpl implements IClearCacheService {

    @Autowired
    private RedissonClient redissonClient;

    @Override
    public <T> void delete(String rmapKey) {
        RMap map = redissonClient.getMap(rmapKey);
        map.delete();
    }

    @Override
    public <T> void delete(String rmapKey, String idKey) {
        RMap<String, T> map = redissonClient.getMap(rmapKey);
        map.remove(idKey);
    }

    @Override
    public void deleteEmpInfo(String empId) {
        String idKey = StrUtil.format("emp_info_id_{}", empId);
        this.delete(CommKeyConstants.EMPINFO_KEY, idKey);
    }

    @Override
    public void deleteNotice(Integer ntId) {
        String idKey = StrUtil.format("nt_id_{}", ntId.toString());
        this.delete(CommKeyConstants.NOTICE_KEY, idKey);
    }

    @Override
    public void deleteProject(Integer prjId) {
        String idKey = StrUtil.format("prj_id_{}", prjId.toString());
        this.delete(CommKeyConstants.PROJECT_KEY, idKey);
    }

    @Override
    public void deletePrjTask(Integer taskId) {
        String idKey = StrUtil.format("task_id_{}", taskId.toString());
        this.delete(CommKeyConstants.PRJTASK_KEY, idKey);
    }
}
