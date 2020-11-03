package com.gunners.epes.service.impl;

import com.gunners.epes.entity.DailyLog;
import com.gunners.epes.entity.TaskEvaInfo;
import com.gunners.epes.entity.vo.DailyLogVo;
import com.gunners.epes.mapper.DailyLogMapper;
import com.gunners.epes.service.IDailyLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author gunners
 * @since 2020-02-27
 */
@Service
public class DailyLogServiceImpl extends ServiceImpl<DailyLogMapper, DailyLog> implements IDailyLogService {

    @Autowired
    private DailyLogMapper dailyLogMapper;

    @Override
    public List<DailyLog> listDailyLog(DailyLogVo dailyLogVo) {
        //分页
        long limit = dailyLogVo.getSize();
        long start = (dailyLogVo.getPage() - 1) * limit;
        List<DailyLog> list = this.dailyLogMapper.listDailyLog(dailyLogVo, start, limit);
        return list;
    }
}
