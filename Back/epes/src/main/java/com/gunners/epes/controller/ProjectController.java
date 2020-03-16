package com.gunners.epes.controller;


import com.gunners.epes.constants.CommKeyConstants;
import com.gunners.epes.constants.SessionKeyConstants;
import com.gunners.epes.entity.Project;
import com.gunners.epes.entity.Response;
import com.gunners.epes.entity.vo.ProjectVo;
import com.gunners.epes.service.IClearCacheService;
import com.gunners.epes.service.IGetCacheService;
import com.gunners.epes.service.IProjectService;
import com.gunners.epes.service.ISaveCacheService;
import com.gunners.epes.utils.SessionUtils;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.time.Instant;
import java.util.List;
import java.util.Objects;

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

    @Autowired
    IGetCacheService getCacheService;

    @Autowired
    IClearCacheService clearCacheService;

    @PostMapping("/save")
    public Response saveProject(Project project){
        project.setCreateTime(Instant.now().getEpochSecond());
        project.setStatus(0);
        project.setReleTaskNum(0);
        projectService.save(project);
        saveCacheService.saveProject(project);
        return Response.ok();
    }

    @PostMapping("/delete")
    public Response deleteProject(Integer prjId){
        projectService.removeById(prjId);
        clearCacheService.deleteProject(prjId);
        return Response.ok();
    }

    /**
     * 页面间传递过滤条件
     * @param projectVo
     * @param session
     * @return
     */
    @PostMapping("/transFilter")
    public Response transmitFilter(ProjectVo projectVo, HttpSession session){
        if (!Objects.isNull(projectVo.getStartTime())) {
            sessionUtils.putIntoSession(session, SessionKeyConstants.PROJECT_START_TIME, projectVo.getStartTime());
        }
        if (!Objects.isNull(projectVo.getEndTime())) {
            sessionUtils.putIntoSession(session, SessionKeyConstants.PROJECT_END_TIME, projectVo.getEndTime());
        }
        if (!Objects.isNull(projectVo.getStatus())) {
            sessionUtils.putIntoSession(session, SessionKeyConstants.PROJECT_STATUS, projectVo.getStatus());
        }
        return Response.ok();
    }

    @GetMapping("/list")
    public Response listProject(HttpSession session, ProjectVo projectVo){

        //获取过滤条件并查询
        projectVo.setStartTime(sessionUtils.getFromSession(session, SessionKeyConstants.PROJECT_START_TIME));
        projectVo.setEndTime(sessionUtils.getFromSession(session, SessionKeyConstants.PROJECT_END_TIME));
        projectVo.setStatus(sessionUtils.getFromSession(session, SessionKeyConstants.PROJECT_STATUS));
        List<Project> list = projectService.listProject(projectVo);

        //清除session过滤条件
        sessionUtils.removeFromSession(session, SessionKeyConstants.PROJECT_START_TIME);
        sessionUtils.removeFromSession(session, SessionKeyConstants.PROJECT_END_TIME);
        sessionUtils.removeFromSession(session, SessionKeyConstants.PROJECT_STATUS);

        return Response.ok(list);
    }

    @PostMapping("/transId")
    public Response transmitId(HttpSession session, Integer prjId){
        sessionUtils.putIntoSession(session, SessionKeyConstants.PROJECT_ID, prjId);
        return Response.ok();
    }

    @GetMapping("/getPrj")
    public Response getProject(HttpSession session){
        Integer prjId = sessionUtils.getFromSession(session, SessionKeyConstants.PROJECT_ID);
        Project project = getCacheService.getProject(prjId);
//        if(Objects.isNull(project)){
//            project = projectService.getById(prjId);
//        }
        return Response.ok(project);
    }

    @PostMapping("/finish")
    //赋值完成时间，更新状态，用project接，更新redis、数据库
    public Response finishProject(HttpSession session){
        Integer prjId = sessionUtils.getFromSession(session, SessionKeyConstants.PROJECT_ID);

        //更新状态、完成时间，更新进redis
        Project project = getCacheService.getProject(prjId);
        project.setFinishTime(Instant.now().getEpochSecond());
        project.setStatus(1);
        saveCacheService.saveProject(project);

        //更新mysql
        projectService.updateProject(project);

        return Response.ok();
    }

    @GetMapping("/listUndo")
    public Response listUndo(){
        List<Project> list = projectService.listUndo();
        return Response.ok(list);
    }

}
