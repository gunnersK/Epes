package com.gunners.epes.service;

import com.gunners.epes.entity.Notice;

public interface IClearCacheService {

    /**
     * 从redis删除信息的公共方法
     * @param rmapKey
     * @param <T>
     */
    <T> void delete(String rmapKey);

    /**
     * 从redis删除信息的公共方法
     * @param rmapKey
     * @param idKey
     * @param <T>
     */
    <T> void delete(String rmapKey, String idKey);

    /**
     * 删除redis中的员工信息
     * @param empId
     */
    void deleteEmpInfo(String empId);

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
