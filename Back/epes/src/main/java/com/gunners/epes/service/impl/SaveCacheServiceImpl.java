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

@Service
public class SaveCacheServiceImpl implements ISaveCacheService {

    @Autowired
    private RedissonClient redissonClient;

    @Override
    public void saveEmpInfo(EmpInfo empInfo) {
        RMap<String, EmpInfo> map = redissonClient.getMap(CommKeyConstants.EMPINFO_KEY);
        String key = StrUtil.format("emp_info_id_{}", empInfo.getEmpId());
        map.put(key, empInfo);
    }

    @Override
    public void saveNotice(Notice notice) {
        RMap<String, Notice> map = redissonClient.getMap(CommKeyConstants.NOTICE_KEY);
        String key = StrUtil.format("nt_id_{}", notice.getNtId().toString());
        map.put(key, notice);
    }

    @Override
    public void saveProject(Project project) {
        //查找项目关联任务数
//        Integer num = prjTaskService.countTaskNum(project.getPrjId());
//        project.setReleTaskNum(num);

        RMap<String, Project> map = redissonClient.getMap(CommKeyConstants.PROJECT_KEY);
        String key = StrUtil.format("prj_id_{}", project.getPrjId().toString());
        map.put(key, project);
    }

    @Override
    public void savePrjTask(PrjTask prjTask) {
        RMap<String, PrjTask> map = redissonClient.getMap(CommKeyConstants.PRJTASK_KEY);
        String key = StrUtil.format("task_id_{}", prjTask.getTaskId().toString());
        map.put(key, prjTask);
    }

    @Override
    public void saveTaskEvaInfo(TaskEvaInfo taskEvaInfo) {
        RMap<String, TaskEvaInfo> map = redissonClient.getMap(CommKeyConstants.TASKEVA_INFO_KEY);
        String key = StrUtil.format("eva_id_{}", taskEvaInfo.getEvaId().toString());
        map.put(key, taskEvaInfo);
    }

    @Override
    public void saveDailyLog(DailyLog dailyLog) {
        RMap<String, DailyLog> map = redissonClient.getMap(CommKeyConstants.DAILY_LOG_KEY);
        String key = StrUtil.format("daily_log_id_{}", dailyLog.getId().toString());
        map.put(key, dailyLog);
    }
}
