package com.gunners.epes.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gunners.epes.constants.CommKeyConstants;
import com.gunners.epes.constants.SessionKeyConstants;
import com.gunners.epes.entity.Notice;
import com.gunners.epes.entity.Response;
import com.gunners.epes.entity.vo.NoticeVo;
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
        noticeService.save(notice);
        saveCacheService.saveNotice(notice);
        return Response.ok();
    }

    @PostMapping("/delete")
    public Response deleteNotice(Integer ntId){
        noticeService.removeById(ntId);
        clearCacheService.deleteNotice(ntId);
        return Response.ok();
    }

    @GetMapping("/list")
    public Response listNotice(NoticeVo noticeVo){
        List<Notice> list = noticeService.listNotice(noticeVo);
        return Response.ok(list);
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

    @GetMapping("/getNtc")
    public Response getNotice(HttpSession session){
        Integer ntId = sessionUtils.getFromSession(session, SessionKeyConstants.NOTICE_ID);
        Notice notice = getCacheService.getNotice(ntId);
        if(Objects.isNull(notice)){
            notice = noticeService.getById(ntId);
        }
        return Response.ok(notice);
    }

}
