package com.gunners.epes.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gunners.epes.constants.SessionKeyConstants;
import com.gunners.epes.entity.Notice;
import com.gunners.epes.entity.Project;
import com.gunners.epes.entity.vo.ProjectVo;
import com.gunners.epes.mapper.ProjectMapper;
import com.gunners.epes.service.IProjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gunners.epes.utils.SessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    SessionUtils sessionUtils;

    @Override
    public List<Project> listProject(ProjectVo projectVo) {
        //设置查询where条件
        QueryWrapper queryWrapper = new QueryWrapper<Project>()
                .select("prj_id", "prj_name")
                .orderByDesc("prj_id")
                .like("prj_name", Objects.isNull(projectVo.getPrjName()) ? "" : projectVo.getPrjName());
        if(!Objects.isNull(projectVo.getStartTime())){
            queryWrapper.ge("create_time", projectVo.getStartTime());
        }
        if(!Objects.isNull(projectVo.getEndTime())){
            queryWrapper.le("create_time", projectVo.getEndTime());
        }
        if(!Objects.isNull(projectVo.getStatus())){
            queryWrapper.eq("status", projectVo.getStatus());
        }

        //获取分页结果
        Page page = new Page(projectVo.getPage(), projectVo.getSize());
        IPage<Project> projectPage = this.page(page, queryWrapper);
        List<Project> list = projectPage.getRecords();

        return list;
    }

    @Override
    public boolean updateProject(Project project) {
        UpdateWrapper updateWrapper = new UpdateWrapper<Project>()
                .eq("prj_id", project.getPrjId());
        return this.update(project, updateWrapper);
    }

    @Override
    public List<Project> listUndo() {
        QueryWrapper queryWrapper = new QueryWrapper<Project>()
                .select("prj_id", "prj_name")
                .eq("status", 0);
        return this.list(queryWrapper);
    }
}
