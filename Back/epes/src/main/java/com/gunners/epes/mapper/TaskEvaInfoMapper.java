package com.gunners.epes.mapper;

import com.gunners.epes.entity.TaskEvaInfo;
import com.gunners.epes.entity.vo.TaskEvaVo;

import java.util.List;

public interface TaskEvaInfoMapper {

    TaskEvaInfo getEvaInfo(Integer evaId);

    List<TaskEvaInfo> listTaskEva(TaskEvaVo taskEvaVo, long start, long limit);

}
