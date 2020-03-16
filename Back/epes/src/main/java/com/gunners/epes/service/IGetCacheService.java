package com.gunners.epes.service;

import com.gunners.epes.entity.Notice;
import com.gunners.epes.entity.PrjTask;
import com.gunners.epes.entity.Project;

public interface IGetCacheService {

    /**
     * 在redis中获取公告
     * @param ntId
     * @return
     */
    Notice getNotice(Integer ntId);

    /**
     * 在redis中获取项目
     * @param prjId
     * @return
     */
    Project getProject(Integer prjId);

    /**
     * 在redis中获取任务
     * @param taskId
     * @return
     */
    PrjTask getPrjTask(Integer taskId);
}


