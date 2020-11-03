package com.gunners.epes.mapper;

import com.gunners.epes.entity.PrjTask;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gunners.epes.entity.vo.PrjTaskVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author gunners
 * @since 2020-02-27
 */
public interface PrjTaskMapper extends BaseMapper<PrjTask> {

    List<PrjTask> listPrjTask(@Param("prjTaskVo") PrjTaskVo prjTaskVo, long start, long limit);

}
