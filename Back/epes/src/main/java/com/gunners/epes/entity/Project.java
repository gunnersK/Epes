package com.gunners.epes.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
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
public class Project implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增prj_id
     */
    @TableId(value = "prj_id", type = IdType.AUTO)
    private Integer prjId;

    private String prjName;

    /**
     * 项目描述
     */
    private String prjDesc;

    private Long createTime;

    private Long finishTime;

    /**
     * 0未关闭，1已关闭
     */
    private Integer status;

    /**
     * 关联任务数
     */
    @TableField(exist = false)
    private Integer releTaskNum;


}
