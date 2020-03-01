package com.gunners.epes.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gunners.epes.entity.Employee;
import com.gunners.epes.entity.Notice;
import com.gunners.epes.mapper.NoticeMapper;
import com.gunners.epes.service.INoticeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jodd.time.TimeUtil;
import org.mockito.internal.matchers.Not;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author gunners
 * @since 2020-02-27
 */
@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements INoticeService {

    @Override
    public List<Notice> searchByKeyword(String keyWord) {
        Wrapper queryWrapper = new QueryWrapper<Notice>().lambda()
                .like(Notice::getTitle, keyWord);
        return baseMapper.selectList(queryWrapper);
    }
}
