package com.gunners.epes.mapper;

import com.gunners.epes.entity.DailyLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gunners.epes.entity.TaskEvaInfo;
import com.gunners.epes.entity.vo.DailyLogVo;
import com.gunners.epes.entity.vo.TaskEvaVo;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author gunners
 * @since 2020-02-27
 */
public interface DailyLogMapper extends BaseMapper<DailyLog> {

    List<DailyLog> listDailyLog(DailyLogVo dailyLogVo, long start, long limit);

}
