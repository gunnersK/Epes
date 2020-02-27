package com.gunners.epes.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author gunners
 * @since 2020-02-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TaskEva implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增eva_id
     */
    @TableId(value = "eva_id", type = IdType.AUTO)
    private Integer evaId;

    private Integer empId;

    /**
     * 关联prj_task表
     */
    private Integer taskId;

    private Integer createTime;

    private Integer finishTime;

    private Integer lastUpdTime;

    /**
     * 任务得分
     */
    private BigDecimal score;

    /**
     * 绩效=任务得分*任务权重
     */
    private BigDecimal performance;

    /**
     * 0未开始，1进行中，2已完成，3已作废
     */
    private Integer status;


}
