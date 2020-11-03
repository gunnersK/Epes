package com.gunners.epes.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gunners.epes.entity.Notice;
import com.gunners.epes.entity.PrjTask;
import com.gunners.epes.entity.Project;
import com.gunners.epes.entity.vo.PrjTaskVo;
import com.gunners.epes.mapper.PrjTaskMapper;
import com.gunners.epes.service.IGetCacheService;
import com.gunners.epes.service.IPrjTaskService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gunners.epes.service.IProjectService;
import com.gunners.epes.service.ISaveCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.PrinterJob;
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

    @Autowired
    IGetCacheService getCacheService;

    @Autowired
    ISaveCacheService saveCacheService;

    @Autowired
    IProjectService projectService;

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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean savePrjTask(PrjTask prjTask) {
        boolean flag = false;

        if(this.save(prjTask)){
            Project project = getCacheService.getProject(prjTask.getPrjId());
            project.setReleTaskNum(project.getReleTaskNum() + 1);
            projectService.updateProject(project);
            saveCacheService.saveProject(project);
            flag = true;
        }
        return flag;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updatePrjTask(PrjTask prjTask) {
        UpdateWrapper updateWrapper = new UpdateWrapper<Project>()
                .eq("task_id", prjTask.getTaskId());
        return this.update(prjTask, updateWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteById(Integer taskId) {
        boolean flag = false;

        if(this.removeById(taskId)){
            PrjTask prjTask = getCacheService.getPrjTask(taskId);
            Project project = getCacheService.getProject(prjTask.getPrjId());
            Integer num = project.getReleTaskNum();
            if(num > 0){
                project.setReleTaskNum(project.getReleTaskNum() - 1);
                projectService.updateProject(project);
                saveCacheService.saveProject(project);
                flag = true;
            }
        }
        return flag;
    }

    @Override
    public List<PrjTask> listUnfinish() {
        QueryWrapper queryWrapper = new QueryWrapper<Notice>()
                .select("task_id", "task_name")
                .eq("status", 0)
                .orderByDesc("task_id");
        return this.list(queryWrapper);
    }
}
