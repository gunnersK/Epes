package com.gunners.epes.service;

import com.gunners.epes.entity.Project;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gunners.epes.entity.vo.ProjectVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author gunners
 * @since 2020-02-27
 */
public interface IProjectService extends IService<Project> {

    List<Project> listProject(ProjectVo projectVo);

    boolean updateProject(Project project);

    List<Project> listUndo();

}
