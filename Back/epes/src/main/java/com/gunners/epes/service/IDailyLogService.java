package com.gunners.epes.service;

import com.gunners.epes.entity.DailyLog;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gunners.epes.entity.vo.DailyLogVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author gunners
 * @since 2020-02-27
 */
public interface IDailyLogService extends IService<DailyLog> {

    List<DailyLog> listDailyLog(DailyLogVo dailyLogVo);

}
