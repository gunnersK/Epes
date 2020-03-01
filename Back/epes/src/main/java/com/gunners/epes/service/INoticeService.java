package com.gunners.epes.service;

import com.gunners.epes.entity.Notice;
import com.baomidou.mybatisplus.extension.service.IService;

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

    public List<Notice> searchByKeyword(String keyWord);
}
