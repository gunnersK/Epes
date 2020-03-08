package com.gunners.epes.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gunners.epes.entity.PrjTask;
import com.gunners.epes.mapper.PrjTaskMapper;
import com.gunners.epes.service.IPrjTaskService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author gunners
 * @since 2020-02-27
 */
@Service
public class PrjTaskServiceImpl extends ServiceImpl<PrjTaskMapper, PrjTask> implements IPrjTaskService {

    @Override
    public Integer countTaskNum(Integer prjId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("prj_id", prjId);
        return this.count(queryWrapper);
    }
}
