package com.gunners.epes.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Accessors(chain = true)
public class TaskEvaInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer evaId;

    private String taskName;

    /**
     * 任务描述
     */
    private String taskDesc;

    private Integer gender;

    private Integer weight;

    private String empName;

    private Integer status;

    /**
     * 任务得分
     */
    private BigDecimal score;

    /**
     * 绩效=任务得分*任务权重
     */
    private BigDecimal performance;

    private Long createTime;

    private Long finishTime;

    private Long lastUpdTime;
}
