package com.gunners.epes.service.impl;

import cn.hutool.core.util.StrUtil;
import com.gunners.epes.constants.CommKeyConstants;
import com.gunners.epes.entity.Notice;
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
    public void saveNotice(Notice notice) {
        RMap<String, Notice> map = redissonClient.getMap(CommKeyConstants.NOTICE_KEY);
        String key = StrUtil.format("nt_id_{}", notice.getNtId().toString());
        map.put(key, notice);
    }

    @Override
    public void saveProject(Project project) {
        //查找项目关联任务数
        Integer num = prjTaskService.countTaskNum(project.getPrjId());
        project.setReleTaskNum(num);

        RMap<String, Project> map = redissonClient.getMap(CommKeyConstants.PROJECT_KEY);
        String key = StrUtil.format("prj_id_{}", project.getPrjId().toString());
        map.put(key, project);
    }
}
