package com.gunners.epes.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.gunners.epes.entity.TaskEva;
import com.gunners.epes.entity.TaskEvaInfo;
import com.gunners.epes.mapper.TaskEvaMapper;
import com.gunners.epes.service.ISaveCacheService;
import com.gunners.epes.service.ITaskEvaInfoService;
import com.gunners.epes.service.ITaskEvaService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import javafx.concurrent.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author gunners
 * @since 2020-02-27
 */
@Service
public class TaskEvaServiceImpl extends ServiceImpl<TaskEvaMapper, TaskEva> implements ITaskEvaService {

    @Autowired
    private ITaskEvaInfoService taskEvaInfoService;

    @Autowired
    ISaveCacheService saveCacheService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveBatch(Integer taskId, String[] empIdList) {
        boolean flag = false;

        Long createTime = Instant.now().getEpochSecond();
        Long lastUpdTime = createTime;
        List<String> idList = Lists.newArrayList(empIdList);

        List<TaskEva> evaList = Lists.newArrayList();
        idList.forEach(empId -> {
            TaskEva eva = new TaskEva().setTaskId(taskId).setEmpId(empId)
                    .setCreateTime(createTime).setLastUpdTime(lastUpdTime).setStatus(0);
            evaList.add(eva);
        });

        if(super.saveBatch(evaList)){
            saveCache(evaList);
            flag = true;
        }

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
     * @param evaList
     */
    private void saveCache(List<TaskEva> evaList){
        evaList.forEach(eva -> {
            TaskEvaInfo taskEvaInfo = taskEvaInfoService.getTaskEvaInfo(eva.getEvaId());
            if(taskEvaInfo.getPerformance().toString().equals("0.00")){
                taskEvaInfo.setPerformance(null);
            }
            if(taskEvaInfo.getFinishTime() == 0){
                taskEvaInfo.setFinishTime(null);
            }
            saveCacheService.saveTaskEvaInfo(taskEvaInfo);
        });
    }
}
