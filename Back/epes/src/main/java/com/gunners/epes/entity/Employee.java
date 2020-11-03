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
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 工号（101200101）
     */
    private String empId;

    private String empName;

    /**
     * 0男，1女
     */
    private Integer gender;

    /**
     * 部门id
     */
    private Integer dpartId;

    /**
     * 联系方式
     */
    private String contact;

    /**
     * 0离职，1在职
     */
    private Integer status;


}
