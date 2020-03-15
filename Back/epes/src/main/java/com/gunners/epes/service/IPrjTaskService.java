package com.gunners.epes.service;

import com.gunners.epes.entity.PrjTask;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gunners.epes.entity.vo.PrjTaskVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author gunners
 * @since 2020-02-27
 */
public interface IPrjTaskService extends IService<PrjTask> {

    Integer countTaskNum(Integer prjId);

    List<PrjTask> listPrjTask(PrjTaskVo prjTaskVo);

}
