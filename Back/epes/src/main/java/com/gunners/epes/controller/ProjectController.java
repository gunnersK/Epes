package com.gunners.epes.controller;


import com.gunners.epes.entity.Project;
import com.gunners.epes.entity.Response;
import com.gunners.epes.service.IProjectService;
import com.gunners.epes.service.ISaveCacheService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

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

}
