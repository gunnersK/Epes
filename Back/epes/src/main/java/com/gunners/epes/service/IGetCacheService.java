package com.gunners.epes.service;

import com.gunners.epes.entity.*;
import org.redisson.api.RSet;

import java.util.Set;

public interface IGetCacheService {

    /**
     * 在redis获取信息的公共方法
     * @param rmapKey
     * @param idKey
     * @param <T>
     * @return
     */
    <T> T get(String rmapKey, String idKey);

    /**
     * 在redis获取set
     * @param setKey
     * @return
     */
    Set getSet(String setKey);

    /**
     * 在redis获取某个信息域的所有key
     * @param rmapKey
     * @return
     */
    Set<String> getAllKeys(String rmapKey);

    /**
     * 在redis中获取公告
     * @param empId
     * @return
     */
    EmpInfo getEmpInfo(String empId);

    /**
     * 在redis中获取公告
     * @param ntId
     * @return
     */
    Notice getNotice(Integer ntId);

    /**
     * 在redis中获取最新公告
     * @return
     */
    Notice getLastNotice();

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

    /**
     * 在redis中获取绩效
     * @param evaId
     * @return
     */
    TaskEvaInfo getTaskEvaInfo(Integer evaId);

    /**
     * 在redis中获取公告
     * @param id
     * @return
     */
    DailyLog getDailyLog(Integer id);
}


