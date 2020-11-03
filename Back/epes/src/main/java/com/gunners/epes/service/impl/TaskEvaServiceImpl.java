package com.gunners.epes.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.gunners.epes.constants.CommKeyConstants;
import com.gunners.epes.constants.LockConstants;
import com.gunners.epes.entity.TaskEva;
import com.gunners.epes.entity.TaskEvaInfo;
import com.gunners.epes.mapper.TaskEvaMapper;
import com.gunners.epes.service.IGetCacheService;
import com.gunners.epes.service.ISaveCacheService;
import com.gunners.epes.service.ITaskEvaInfoService;
import com.gunners.epes.service.ITaskEvaService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gunners.epes.utils.LockUtils;
import com.gunners.epes.utils.TimeUtils;
import javafx.concurrent.Task;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author gunners
 * @since 2020-02-27
 */
@Service
@Slf4j
public class TaskEvaServiceImpl extends ServiceImpl<TaskEvaMapper, TaskEva> implements ITaskEvaService {

    @Autowired
    private TaskEvaMapper taskEvaMapper;

    @Autowired
    private ITaskEvaInfoService taskEvaInfoService;

    @Autowired
    private ISaveCacheService saveCacheService;

    @Autowired
    private IGetCacheService getCacheService;

    @Autowired
    LockUtils lockUtils;

    @Override
    public List<Integer> queryChartData(Integer year, Integer dpartId, String empId) {
        List<Integer> dataList = Lists.newArrayList();
        long start = System.currentTimeMillis();
        for(int i = 1; i <= 12; i++){
            Integer num = 0;
            Long startTime = TimeUtils.getSecondByYear(StrUtil.format("{}-{}-1", year, i));
            Long endTime = 0L;
            if(i != 12){
                endTime = TimeUtils.getSecondByYear(StrUtil.format("{}-{}-1", year, (i + 1)));
            } else {
                endTime = TimeUtils.getSecondByYear(StrUtil.format("{}-1-1", (year + 1)));
            }
            num = taskEvaMapper.queryChartData(startTime, endTime, dpartId, empId);
            if(Objects.isNull(num)){
                dataList.add(0);
            } else {
                dataList.add(num);
            }
        }

        long end = System.currentTimeMillis();
        log.info(StrUtil.format("query chart data cost {} ms", (end - start)));
        return dataList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveBatch(Integer taskId, String[] empIdList) {
        boolean flag = false;

        Long createTime = Instant.now().getEpochSecond();
        Long lastUpdTime = createTime;
        List<String> idList = Lists.newArrayList(empIdList);

        //保存任务分配记录
        idList.forEach(empId -> {
            TaskEva eva = new TaskEva().setTaskId(taskId).setEmpId(empId)
                    .setCreateTime(createTime).setLastUpdTime(lastUpdTime).setStatus(0);
            String taskEmp = StrUtil.format("{}_{}", taskId, empId);
            String lockName = StrUtil.format("{}_{}", LockConstants.LOCK_EVA, taskEmp);
            RLock lock = lockUtils.getLock(lockName);
            try{
                //加锁并到任务分配set里判断当前任务是否已存在
                lock.tryLock(3, TimeUnit.SECONDS);
                Set<String> taskEmpSet = getCacheService.getSet(CommKeyConstants.TASK_EMP_SET); //任务分配set
                if(!taskEmpSet.contains(taskEmp)){

                    //若set里不存在该任务就保存任务，有就跳过该任务
                    this.save(eva);
                    this.saveCache(eva);
                    taskEmpSet.add(taskEmp);
                }
            } catch (Exception e){
                e.printStackTrace();
            } finally {
                if(lock.isHeldByCurrentThread() && lock.isLocked()){
                    lock.unlock();
                }
            }
        });

        flag = true;

        return flag;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateTaskEva(TaskEvaInfo taskEvaInfo) {
        boolean flag = false;

        //从mysql获取TaskEva对象
        Wrapper<TaskEva> queryWrapper = new QueryWrapper<TaskEva>().lambda()
                .eq(TaskEva::getEvaId, taskEvaInfo.getEvaId());
        TaskEva taskEva = this.getOne(queryWrapper);
        if(!Objects.isNull(taskEva)){
            taskEva.setPerformance(taskEvaInfo.getPerformance())
                    .setScore(taskEvaInfo.getScore())
                    .setFinishTime(taskEvaInfo.getFinishTime())
                    .setStatus(taskEvaInfo.getStatus());

            //更新mysql
            if(this.getBaseMapper().updateById(taskEva) > 0){
                flag = true;
            }
        }
        return flag;
    }

    /**
     * 保存绩效任务信息到redis
     * @param taskEva
     */
    private void saveCache(TaskEva taskEva){
        TaskEvaInfo taskEvaInfo = taskEvaInfoService.getTaskEvaInfo(taskEva.getEvaId());
        if(taskEvaInfo.getPerformance().toString().equals("0.00")){
            taskEvaInfo.setPerformance(null);
        }
        if(taskEvaInfo.getFinishTime() == 0){
            taskEvaInfo.setFinishTime(null);
        }
        saveCacheService.saveTaskEvaInfo(taskEvaInfo);
    }
}
