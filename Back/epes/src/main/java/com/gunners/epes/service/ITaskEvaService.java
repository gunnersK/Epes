package com.gunners.epes.service;

import com.gunners.epes.entity.TaskEva;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gunners.epes.entity.TaskEvaInfo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author gunners
 * @since 2020-02-27
 */
public interface ITaskEvaService extends IService<TaskEva> {

    boolean saveBatch(Integer taskId, String[] empIdList);

    boolean updateTaskEva(TaskEvaInfo taskEvaInfo);

}
