package com.gunners.epes.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class EmpInfo {

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

    private String dpartName;

    /**
     * 角色（0管理员，1部门主管，2普通员工）
     */
    private Integer role;

    /**
     * 联系方式
     */
    private String contact;
}
