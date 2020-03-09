package com.gunners.epes.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gunners.epes.entity.Notice;
import com.gunners.epes.entity.Project;
import com.gunners.epes.entity.vo.ProjectVo;
import com.gunners.epes.mapper.ProjectMapper;
import com.gunners.epes.service.IProjectService;
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
public class ProjectServiceImpl extends ServiceImpl<ProjectMapper, Project> implements IProjectService {

    @Override
    public List<Project> listProject(ProjectVo projectVo) {
        QueryWrapper queryWrapper = new QueryWrapper<Notice>();
        queryWrapper.orderByDesc("prj_id");
        queryWrapper.like("prj_name", Objects.isNull(projectVo.getPrjName()) ? "" : projectVo.getPrjName());
        queryWrapper.ge("create_time", projectVo.getStartTime());
        queryWrapper.le("create_time", projectVo.getEndTime());
        Page page = new Page(projectVo.getPage(), projectVo.getSize());
        IPage<Project> projectPage = this.page(page, queryWrapper);
        List<Project> list = projectPage.getRecords();
        return list;
    }
}
