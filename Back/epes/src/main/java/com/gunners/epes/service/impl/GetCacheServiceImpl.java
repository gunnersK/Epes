package com.gunners.epes.service.impl;

import cn.hutool.core.util.StrUtil;
import com.gunners.epes.constants.CommKeyConstants;
import com.gunners.epes.entity.Notice;
import com.gunners.epes.entity.Project;
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
}
