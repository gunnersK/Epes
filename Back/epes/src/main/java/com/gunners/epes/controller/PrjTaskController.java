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

}
