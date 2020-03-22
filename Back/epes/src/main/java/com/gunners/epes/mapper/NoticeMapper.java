package com.gunners.epes.mapper;

import com.gunners.epes.entity.Notice;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author gunners
 * @since 2020-02-27
 */
public interface NoticeMapper extends BaseMapper<Notice> {

    Notice getLastNotice();

}
