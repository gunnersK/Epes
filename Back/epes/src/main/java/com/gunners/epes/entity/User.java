package com.gunners.epes.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
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
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增user_id
     */
    @TableId(value = "user_id", type = IdType.AUTO)
    private Integer userId;

    /**
     * 员工id
     */
    private String empId;

    private String password;

    /**
     * 角色（0管理员，1部门主管，2普通员工）
     */
    private Integer role;

    private Integer lastLoginTime;

    /**
     * （0失效，1生效）
     */
    @TableField("isActive")
    private Integer isActive;


}
