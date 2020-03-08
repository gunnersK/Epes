package com.gunners.epes.service;

import com.gunners.epes.entity.Notice;
import com.gunners.epes.entity.Project;

public interface ISaveCacheService {

    /**
     * 保存公告到redis
     * @param notice
     * @return
     */
    void saveNotice(Notice notice);

    /**
     * 保存项目到redis
     * @param project
     */
    void saveProject(Project project);

}
