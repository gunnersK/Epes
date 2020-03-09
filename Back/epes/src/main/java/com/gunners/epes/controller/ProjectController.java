package com.gunners.epes.controller;


import com.gunners.epes.constants.SessionKeyConstants;
import com.gunners.epes.entity.Project;
import com.gunners.epes.entity.Response;
import com.gunners.epes.entity.vo.ProjectVo;
import com.gunners.epes.service.IProjectService;
import com.gunners.epes.service.ISaveCacheService;
import com.gunners.epes.utils.SessionUtils;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author gunners
 * @since 2020-02-27
 */
@RestController
@RequestMapping("/project")
public class ProjectController {

    @Autowired
    SessionUtils sessionUtils;

    @Autowired
    IProjectService projectService;

    @Autowired
    ISaveCacheService saveCacheService;

    @PostMapping("/save")
    public Response saveProject(Project project){
        project.setCreateTime(Instant.now().getEpochSecond());
        projectService.save(project);
        saveCacheService.saveProject(project);
        return Response.ok();
    }

    @GetMapping("/list")
    public Response listProject(ProjectVo projectVo){
        Long startTime = (Long) sessionUtils.getFromSession(SessionKeyConstants.PROJECT_START_TIME);
        Long endTime = (Long) sessionUtils.getFromSession(SessionKeyConstants.PROJECT_END_TIME);
        projectVo.setStartTime(startTime);
        projectVo.setEndTime(endTime);
        List<Project> list = projectService.listProject(projectVo);
        return Response.ok(list);
    }

}
