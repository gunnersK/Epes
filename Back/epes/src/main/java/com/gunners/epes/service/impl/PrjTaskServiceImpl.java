package com.gunners.epes.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gunners.epes.entity.PrjTask;
import com.gunners.epes.entity.Project;
import com.gunners.epes.entity.vo.PrjTaskVo;
import com.gunners.epes.mapper.PrjTaskMapper;
import com.gunners.epes.service.IPrjTaskService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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
public class PrjTaskServiceImpl extends ServiceImpl<PrjTaskMapper, PrjTask> implements IPrjTaskService {

    @Override
    public Integer countTaskNum(Integer prjId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("prj_id", prjId);
        return this.count(queryWrapper);
    }

    @Override
    public List<PrjTask> listPrjTask(PrjTaskVo prjTaskVo) {
        //分页
        long limit = prjTaskVo.getSize();
        long start = (prjTaskVo.getPage() - 1) * limit;
        List<PrjTask> list = this.baseMapper.listPrjTask(prjTaskVo, start, limit);
        return list;
    }
}
