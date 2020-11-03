package com.gunners.epes.service;

import com.gunners.epes.entity.TaskEvaInfo;
import com.gunners.epes.entity.vo.TaskEvaVo;

import java.util.List;

public interface ITaskEvaInfoService {

    TaskEvaInfo getTaskEvaInfo(Integer evaId);

    List<TaskEvaInfo> listTaskEva(TaskEvaVo taskEvaVo);
}
