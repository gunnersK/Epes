package com.gunners.epes.service;

import com.gunners.epes.entity.Notice;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gunners.epes.entity.vo.NoticeVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author gunners
 * @since 2020-02-27
 */
public interface INoticeService extends IService<Notice> {

    List<Notice> listNotice(NoticeVo noticeVo);

    Notice getLastNotice();

}
