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

    private String empId;

    /**
     * 关联prj_task表
     */
    private Integer taskId;

    private Long createTime;

    private Long finishTime;

    private Long lastUpdTime;

    /**
     * 任务得分
     */
    private BigDecimal score;

    /**
     * 绩效=任务得分*任务权重
     */
    private BigDecimal performance;

    /**
     * 0进行中，1已完成
     */
    private Integer status;


}
