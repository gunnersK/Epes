package com.gunners.epes.service.impl;

import com.gunners.epes.entity.PrjTask;
import com.gunners.epes.entity.TaskEvaInfo;
import com.gunners.epes.entity.vo.TaskEvaVo;
import com.gunners.epes.mapper.TaskEvaInfoMapper;
import com.gunners.epes.service.ITaskEvaInfoService;
import org.nustaq.serialization.annotations.Serialize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskEvaInfoServiceImpl implements ITaskEvaInfoService {

    @Autowired
    private TaskEvaInfoMapper taskEvaInfoMapper;

    @Override
    public TaskEvaInfo getTaskEvaInfo(Integer evaId) {
        return taskEvaInfoMapper.getEvaInfo(evaId);
    }

    @Override
    public List<TaskEvaInfo> listTaskEva(TaskEvaVo taskEvaVo) {
        //分页
        long limit = taskEvaVo.getSize();
        long start = (taskEvaVo.getPage() - 1) * limit;
        List<TaskEvaInfo> list = this.taskEvaInfoMapper.listTaskEva(taskEvaVo, start, limit);
        return list;
    }
}
