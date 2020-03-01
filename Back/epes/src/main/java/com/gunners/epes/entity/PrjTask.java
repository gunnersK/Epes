package com.gunners.epes.entity;

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
public class PrjTask implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增task_id
     */
    @TableId(value = "task_id", type = IdType.AUTO)
    private Integer taskId;

    private String taskName;

    /**
     * 关联project
     */
    private Integer prjId;

    /**
     * 权重
     */
    private Integer weight;

    private Long createTime;

    private Long finishTime;

    /**
     * 任务描述
     */
    private String taskDesc;

    /**
     * 评分标准说明
     */
    private String scoreDesc;

    /**
     * 0未开始，1进行中，2已完成，3已作废
     */
    private Integer status;


}
