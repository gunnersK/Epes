package com.gunners.epes.service;

import com.gunners.epes.entity.Notice;

public interface IClearCacheService {

    /**
     * 删除redis中的公告
     * @param ntId
     */
    void deleteNotice(Integer ntId);

    /**
     * 删除redis中的项目
     * @param prjId
     */
    void deleteProject(Integer prjId);

    /**
     * 删除redis中的项目任务
     * @param taskId
     */
    void deletePrjTask(Integer taskId);
}
