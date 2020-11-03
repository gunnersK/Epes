package com.gunners.epes.service;

import com.gunners.epes.entity.*;

public interface ISaveCacheService {

    /**
     * 保存到redis的公用方法
     * @param rmapKey
     * @param idKey
     * @param entity
     * @param <T>
     */
    <T> void save(String rmapKey, String idKey, T entity);

    /**
     * 保存员工信息到redis
     * @param empInfo
     * @return
     */
    void saveEmpInfo(EmpInfo empInfo);

    /**
     * 保存公告到redis
     * @param notice
     * @return
     */
    void saveNotice(Notice notice);

    /**
     * 保存最新公告
     * @param notice
     */
    void saveLastNotice(Notice notice);

    /**
     * 保存项目到redis
     * @param project
     */
    void saveProject(Project project);

    /**
     * 保存任务到redis
     * @param prjTask
     */
    void savePrjTask(PrjTask prjTask);

    /**
     * 保存绩效信息到redis
     * @param taskEvaInfo
     */
    void saveTaskEvaInfo(TaskEvaInfo taskEvaInfo);

    /**
     * 保存员工日志到redis
     * @param dailyLog
     */
    void saveDailyLog(DailyLog dailyLog);
}
