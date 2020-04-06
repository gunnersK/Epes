package com.gunners.epes.service.impl;

import cn.hutool.core.util.StrUtil;
import com.gunners.epes.constants.CommKeyConstants;
import com.gunners.epes.entity.*;
import com.gunners.epes.mapper.ProjectMapper;
import com.gunners.epes.service.IPrjTaskService;
import com.gunners.epes.service.ISaveCacheService;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SaveCacheServiceImpl implements ISaveCacheService {

    @Autowired
    private RedissonClient redissonClient;

    @Override
    public <T> void save(String rmapKey, String idKey, T entity) {
        RMap<String, T> map = redissonClient.getMap(rmapKey);
        map.put(idKey, entity);
    }

    @Override
    public void saveEmpInfo(EmpInfo empInfo) {
        String idkey = StrUtil.format("emp_info_id_{}", empInfo.getEmpId());
        this.save(CommKeyConstants.EMPINFO_KEY, idkey, empInfo);
    }

    @Override
    public void saveNotice(Notice notice) {
        String idkey = StrUtil.format("nt_id_{}", notice.getNtId().toString());
        this.save(CommKeyConstants.NOTICE_KEY, idkey, notice);
    }

    @Override
    public void saveLastNotice(Notice notice) {
        this.save(CommKeyConstants.LAST_NT, CommKeyConstants.LAST_NT, notice);
    }

    @Override
    public void saveProject(Project project) {
        String idkey = StrUtil.format("prj_id_{}", project.getPrjId().toString());
        this.save(CommKeyConstants.PROJECT_KEY, idkey, project);
    }

    @Override
    public void savePrjTask(PrjTask prjTask) {
        String idkey = StrUtil.format("task_id_{}", prjTask.getTaskId().toString());
        this.save(CommKeyConstants.PRJTASK_KEY, idkey, prjTask);
    }

    @Override
    public void saveTaskEvaInfo(TaskEvaInfo taskEvaInfo) {
        String idkey = StrUtil.format("eva_id_{}", taskEvaInfo.getEvaId().toString());
        this.save(CommKeyConstants.TASKEVA_INFO_KEY, idkey, taskEvaInfo);
    }

    @Override
    public void saveDailyLog(DailyLog dailyLog) {
        String idkey = StrUtil.format("log_id_{}", dailyLog.getId().toString());
        this.save(CommKeyConstants.DAILY_LOG_KEY, idkey, dailyLog);
    }

}
