package com.gunners.epes.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gunners.epes.constants.CommKeyConstants;
import com.gunners.epes.constants.LockConstants;
import com.gunners.epes.entity.Employee;
import com.gunners.epes.entity.Notice;
import com.gunners.epes.entity.vo.NoticeVo;
import com.gunners.epes.mapper.NoticeMapper;
import com.gunners.epes.service.IClearCacheService;
import com.gunners.epes.service.IGetCacheService;
import com.gunners.epes.service.INoticeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gunners.epes.service.ISaveCacheService;
import com.gunners.epes.utils.LockUtils;
import jodd.time.TimeUtil;
import org.mockito.internal.matchers.Not;
import org.redisson.RedissonLock;
import org.redisson.api.RLock;
import org.redisson.api.RSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Struct;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;

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

    @Autowired
    IGetCacheService getCacheService;

    @Autowired
    ISaveCacheService saveCacheService;

    @Autowired
    IClearCacheService clearCacheService;

    @Autowired
    LockUtils lockUtils;

    @Override
    public boolean saveNotice(Notice notice) {
        //新公告入库
        this.save(notice);

        //更新redis中的最新公告
        saveCacheService.save(CommKeyConstants.LAST_NT, CommKeyConstants.LAST_NT, notice);

        //在redis保存未读set
        Integer ntId = notice.getNtId();
        String ntSetKey = StrUtil.format("{}_{}", CommKeyConstants.UNREAD_NT, ntId);
        Set unreadSet = getCacheService.getSet(ntSetKey);
        Set empIdSet = getCacheService.getAllKeys(CommKeyConstants.EMPINFO_KEY);  //获取所有employeeId的set
        unreadSet.addAll(empIdSet);

        return true;
    }

    @Override
    public boolean deleteById(Integer ntId) {
        Notice notice = this.getLastNotice("redis");

        if(this.removeById(ntId)){
            //判断删除的是否为最新公告,是就从mysql拿最近一条的公告放进redis
            if(notice.getNtId().equals(ntId)){
               notice = this.getLastNotice("mysql");
               if(!Objects.isNull(notice)){
                   saveCacheService.save(CommKeyConstants.LAST_NT, "last_nt", notice);
               } else {
                   clearCacheService.delete(CommKeyConstants.LAST_NT, "last_nt");
               }
            }

            //从redis删除已读未读set
            String readSetKey = StrUtil.format("{}_{}", CommKeyConstants.READ_NT, ntId);
            String unreadSetKey = StrUtil.format("{}_{}", CommKeyConstants.UNREAD_NT, ntId);
            clearCacheService.delete(readSetKey);
            clearCacheService.delete(unreadSetKey);
        }
        return true;
    }

    @Override
    public List<Notice> listNotice(NoticeVo noticeVo) {
        QueryWrapper queryWrapper = new QueryWrapper<Notice>()
                .select("nt_id", "title", "create_time")
                .orderByDesc("nt_id")
                .like("title", Objects.isNull(noticeVo.getTitle()) ? "" : noticeVo.getTitle());
        if(!Objects.isNull(noticeVo.getTitle())){
            queryWrapper.ge("title", noticeVo.getTitle());
        }
        if(!Objects.isNull(noticeVo.getStartTime())){
            queryWrapper.ge("create_time", noticeVo.getStartTime());
        }
        if(!Objects.isNull(noticeVo.getEndTime())){
            queryWrapper.le("create_time", noticeVo.getEndTime());
        }
        Page page = new Page(noticeVo.getPage(), noticeVo.getSize());
        IPage<Notice> noticePage = this.page(page, queryWrapper);
        List<Notice> list = noticePage.getRecords();
        return list;
    }

    @Override
    public Notice getSpecNotice(String empId, Integer ntId) {
        Notice notice = getCacheService.getNotice(ntId);
        String readSetKey = StrUtil.format("{}_{}", CommKeyConstants.READ_NT, ntId);
        String unreadSetKey = StrUtil.format("{}_{}", CommKeyConstants.UNREAD_NT, ntId);

        String lockName = StrUtil.format("{}_{}", LockConstants.LOCK_NT, ntId);
        RLock lock = lockUtils.getLock(lockName);
        try {
            boolean f;
            //上锁并统计已读未读人数
            if(f = lock.tryLock(3, TimeUnit.SECONDS)){ //加一个等待锁的时间，其他线程就可以稍等一会，而不是直接不拿公告
                System.out.println(Thread.currentThread().getName()+"-start-"+f);
                Set readSet = getCacheService.getSet(readSetKey);  //已读set
                Set unreadSet = getCacheService.getSet(unreadSetKey);  //未读set
                notice.setReadNum(readSet.size());
                notice.setUnreadNum(unreadSet.size());
                empId = StrUtil.format("emp_info_id_{}", empId);
                unreadSet.remove(empId);
                readSet.add(empId);
            } else{
                notice = null;
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if(lock.isHeldByCurrentThread() && lock.isLocked()){
                lock.unlock();
            }
        }

        return notice;
    }

    @Override
    public Notice getLastNotice(String source) {
        Notice notice = null;
        if(source.equals("redis")){
            notice = getCacheService.getLastNotice();
        } else if (source.equals("mysql")){
            notice = this.getBaseMapper().getLastNotice();
        }
        return notice;
    }
}
