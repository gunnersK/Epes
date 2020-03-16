package com.gunners.epes.service.impl;

import cn.hutool.core.util.StrUtil;
import com.gunners.epes.constants.CommKeyConstants;
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
    public void deleteNotice(Integer ntId) {
        RMap<String, Notice> map = redissonClient.getMap(CommKeyConstants.NOTICE_KEY);
        String key = StrUtil.format("nt_id_{}", ntId.toString());
        map.remove(key);
    }

    @Override
    public void deleteProject(Integer prjId) {
        RMap<String, Project> map = redissonClient.getMap(CommKeyConstants.PROJECT_KEY);
        String key = StrUtil.format("prj_id_{}", prjId.toString());
        map.remove(key);
    }

    @Override
    public void deletePrjTask(Integer taskId) {
        RMap<String, PrjTask> map = redissonClient.getMap(CommKeyConstants.PRJTASK_KEY);
        String key = StrUtil.format("task_id_{}", taskId.toString());
        map.remove(key);
    }
}
