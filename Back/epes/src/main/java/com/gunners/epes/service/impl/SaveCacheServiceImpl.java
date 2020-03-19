package com.gunners.epes.service.impl;

import cn.hutool.core.util.StrUtil;
import com.gunners.epes.constants.CommKeyConstants;
import com.gunners.epes.entity.EmpInfo;
import com.gunners.epes.entity.Notice;
import com.gunners.epes.entity.PrjTask;
import com.gunners.epes.entity.Project;
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

    @Autowired
    private IPrjTaskService prjTaskService;

    @Override
    public void saveEmpiInfo(EmpInfo empInfo) {
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
}
