package com.gunners.epes.controller;


import com.gunners.epes.entity.Notice;
import com.gunners.epes.entity.Response;
import com.gunners.epes.service.INoticeService;
import com.gunners.epes.utils.RedissonUtils;
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
@RequestMapping("/notice")
public class NoticeController {

    @Autowired
    RedissonUtils redissonUtils;

    @Autowired
    INoticeService noticeService;

    @PostMapping("/save")
    public Response saveNotice(Notice notice){
        notice.setCreateTime(Instant.now().getEpochSecond());
        noticeService.save(notice);
        return Response.ok();
    }

    @PostMapping("/delete")
    public Response deleteNotice(Integer ntId){
        noticeService.removeById(ntId);
        return Response.ok();
    }

    @GetMapping("/list")
    public Response listNotice(){
        List<Notice> list = noticeService.list();
        return Response.ok(list);
    }

    @PostMapping("/transId")
    public Response transmitId(Integer ntId){
        redissonUtils.setTransmitId("admin_id", "nt_id", String.valueOf(ntId));
        return Response.ok();
    }

    @GetMapping("/getNtc")
    public Response getNotice(){
        Integer ntId = Integer.valueOf(redissonUtils.getTransmitId("admin_id", "nt_id"));
        Notice notice = noticeService.getById(ntId);
        return Response.ok(notice);
    }

    @GetMapping("/search")
    public Response searchNotice(String keyWord){
        List<Notice> list = noticeService.searchByKeyword(keyWord);
        return Response.ok(list);
    }

}
