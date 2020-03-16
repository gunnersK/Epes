package com.gunners.epes.controller;


import com.gunners.epes.constants.SessionKeyConstants;
import com.gunners.epes.entity.PrjTask;
import com.gunners.epes.entity.Project;
import com.gunners.epes.entity.Response;
import com.gunners.epes.entity.vo.PrjTaskVo;
import com.gunners.epes.entity.vo.ProjectVo;
import com.gunners.epes.service.*;
import com.gunners.epes.utils.SessionUtils;
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
@RequestMapping("/prjTask")
public class PrjTaskController {

    @Autowired
    SessionUtils sessionUtils;

    @Autowired
    IPrjTaskService prjTaskService ;

    @Autowired
    ISaveCacheService saveCacheService;

    @Autowired
    IGetCacheService getCacheService;

    @Autowired
    IClearCacheService clearCacheService;

    @PostMapping("/save")
    public Response save(PrjTask prjTask){
        prjTask.setCreateTime(Instant.now().getEpochSecond());
        prjTask.setStatus(0);
        prjTaskService.save(prjTask);
        saveCacheService.savePrjTask(prjTask);
        return Response.ok();
    }

    @PostMapping("/delete")
    public Response deleteProject(Integer taskId){
        prjTaskService.removeById(taskId);
        clearCacheService.deleteProject(taskId);
        return Response.ok();
    }

    /**
     * 页面间传递过滤条件
     * @param prjTaskVo
     * @param session
     * @return
     */
    @PostMapping("/transFilter")
    public Response transmitFilter(PrjTaskVo prjTaskVo, HttpSession session){
        if (!Objects.isNull(prjTaskVo.getStartTime())) {
            sessionUtils.putIntoSession(session, SessionKeyConstants.PRJTASK_START_TIME, prjTaskVo.getStartTime());
        }
        if (!Objects.isNull(prjTaskVo.getEndTime())) {
            sessionUtils.putIntoSession(session, SessionKeyConstants.PRJTASK_END_TIME, prjTaskVo.getEndTime());
        }
        if (!Objects.isNull(prjTaskVo.getPrjId())) {
            sessionUtils.putIntoSession(session, SessionKeyConstants.PRJTASK_PRJ_ID, prjTaskVo.getPrjId());
        }
        if (!Objects.isNull(prjTaskVo.getStatus())) {
            sessionUtils.putIntoSession(session, SessionKeyConstants.PRJTASK_STATUS, prjTaskVo.getStatus());
        }
        return Response.ok();
    }

    @GetMapping("/list")
    public Response listProject(HttpSession session, PrjTaskVo prjTaskVo){

        //获取过滤条件并查询
        prjTaskVo.setStartTime(sessionUtils.getFromSession(session, SessionKeyConstants.PRJTASK_START_TIME));
        prjTaskVo.setEndTime(sessionUtils.getFromSession(session, SessionKeyConstants.PRJTASK_END_TIME));
        prjTaskVo.setPrjId(sessionUtils.getFromSession(session, SessionKeyConstants.PRJTASK_PRJ_ID));
        prjTaskVo.setStatus(sessionUtils.getFromSession(session, SessionKeyConstants.PRJTASK_STATUS));
        List<PrjTask> list = prjTaskService.listPrjTask(prjTaskVo);

        //清除session过滤条件
        sessionUtils.removeFromSession(session, SessionKeyConstants.PRJTASK_START_TIME);
        sessionUtils.removeFromSession(session, SessionKeyConstants.PRJTASK_END_TIME);
        sessionUtils.removeFromSession(session, SessionKeyConstants.PRJTASK_PRJ_ID);
        sessionUtils.removeFromSession(session, SessionKeyConstants.PRJTASK_STATUS);

        return Response.ok(list);
    }

    @PostMapping("/transId")
    public Response transmitId(HttpSession session, Integer taskId){
        sessionUtils.putIntoSession(session, SessionKeyConstants.PRJTASK_ID, taskId);
        return Response.ok();
    }

    @GetMapping("/getTask")
    public Response getProject(HttpSession session){
        Integer taskId = sessionUtils.getFromSession(session, SessionKeyConstants.PRJTASK_ID);
        PrjTask prjTask = getCacheService.getPrjTask(taskId);
//        if(Objects.isNull(prjTask)){
//            prjTask = prjTaskService.getById(taskId);
//        }
        return Response.ok(prjTask);
    }

    @PostMapping("/finish")
    //赋值完成时间，更新状态，用prjTask接，更新redis、数据库
    public Response finishProject(HttpSession session){
        Integer taskId = sessionUtils.getFromSession(session, SessionKeyConstants.PRJTASK_ID);

        //更新状态、完成时间，更新进redis
        PrjTask prjTask = getCacheService.getPrjTask(taskId);
        prjTask.setFinishTime(Instant.now().getEpochSecond());
        prjTask.setStatus(1);
        saveCacheService.savePrjTask(prjTask);

        //更新mysql
        prjTaskService.updatePrjTask(prjTask);

        return Response.ok();
    }

}
