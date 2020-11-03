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

    /**
     * 保存公告
     * @param notice
     * @return
     */
    boolean saveNotice(Notice notice);

    /**
     * 通过id删除
     * @param ntId
     * @return
     */
    boolean deleteById(Integer ntId);

    /**
     * 条件分页查询公告
     * @param noticeVo
     * @return
     */
    List<Notice> listNotice(NoticeVo noticeVo);

    /**
     * 获取指定公告
     * @param ntId
     * @return
     */
    Notice getSpecNotice(String empId, Integer ntId);

    /**
     * 获取最新公告
     * @param source
     * @return
     */
    Notice getLastNotice(String source);

}
