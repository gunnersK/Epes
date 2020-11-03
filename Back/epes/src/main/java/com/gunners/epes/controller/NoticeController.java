package com.gunners.epes.controller;


import com.gunners.epes.constants.SessionKeyConstants;
import com.gunners.epes.entity.EmpInfo;
import com.gunners.epes.entity.Notice;
import com.gunners.epes.entity.Response;
import com.gunners.epes.entity.vo.NoticeVo;
import com.gunners.epes.entity.vo.ProjectVo;
import com.gunners.epes.service.IClearCacheService;
import com.gunners.epes.service.IGetCacheService;
import com.gunners.epes.service.INoticeService;
import com.gunners.epes.service.ISaveCacheService;
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
@RequestMapping("/notice")
public class NoticeController {

    @Autowired
    SessionUtils sessionUtils;

    @Autowired
    INoticeService noticeService;

    @Autowired
    ISaveCacheService saveCacheService;

    @Autowired
    IGetCacheService getCacheService;

    @Autowired
    IClearCacheService clearCacheService;

    @PostMapping("/save")
    public Response saveNotice(Notice notice){
        notice.setCreateTime(Instant.now().getEpochSecond());
        noticeService.saveNotice(notice);
        saveCacheService.saveNotice(notice);
        return Response.ok();
    }

    @PostMapping("/delete")
    public Response deleteNotice(Integer ntId){
        noticeService.deleteById(ntId);
        clearCacheService.deleteNotice(ntId);
        return Response.ok();
    }

    @GetMapping("/list")
    public Response listNotice(HttpSession session, NoticeVo noticeVo){
        //获取过滤条件并查询
        noticeVo.setStartTime(sessionUtils.getFromSession(session, SessionKeyConstants.NOTICE_START_TIME));
        noticeVo.setEndTime(sessionUtils.getFromSession(session, SessionKeyConstants.NOTICE_END_TIME));
        List<Notice> list = noticeService.listNotice(noticeVo);

        return Response.ok(list);
    }

    /**
     * 页面间传递过滤条件
     * @param noticeVo
     * @param session
     * @return
     */
    @PostMapping("/transFilter")
    public Response transmitFilter(NoticeVo noticeVo, HttpSession session){
        clearFilter(session);
        if (!Objects.isNull(noticeVo.getStartTime())) {
            sessionUtils.putIntoSession(session, SessionKeyConstants.NOTICE_START_TIME, noticeVo.getStartTime());
        }
        if (!Objects.isNull(noticeVo.getEndTime())) {
            sessionUtils.putIntoSession(session, SessionKeyConstants.NOTICE_END_TIME, noticeVo.getEndTime());
        }
        return Response.ok();
    }

    /**
     * 页面间传递公告id
     * @param ntId
     * @return
     */
    @PostMapping("/transId")
    public Response transmitId(HttpSession session, Integer ntId){
        sessionUtils.putIntoSession(session, SessionKeyConstants.NOTICE_ID, ntId);
        return Response.ok();
    }

    /**
     * 公告item详情
     * @param session
     * @return
     */
    @GetMapping("/getNtc")
    public Response getNotice(HttpSession session){
        Integer ntId = sessionUtils.getFromSession(session, SessionKeyConstants.NOTICE_ID);
        EmpInfo empInfo = sessionUtils.getFromSession(session, SessionKeyConstants.EMP_INFO);
        String empId = empInfo.getEmpId();
        Notice notice = noticeService.getSpecNotice(empId, ntId);
        return Response.ok(notice);
    }

    /**
     * 首页定时获取最新公告
     * @return
     */
    @GetMapping("/lastNtc")
    public Response getLastNotice(){
        Notice notice = noticeService.getLastNotice("redis");
        return Response.ok(notice);
    }

    /**
     * 清除所有过滤条件
     * @param session
     * @return
     */
    @PostMapping("clearAllFilter")
    private void clearFilter(HttpSession session){
        sessionUtils.removeFromSession(session, SessionKeyConstants.NOTICE_START_TIME);
        sessionUtils.removeFromSession(session, SessionKeyConstants.NOTICE_END_TIME);
    }

}
