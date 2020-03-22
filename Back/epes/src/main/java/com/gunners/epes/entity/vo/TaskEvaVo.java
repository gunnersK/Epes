package com.gunners.epes.entity.vo;

import lombok.Data;

@Data
public class TaskEvaVo extends PageVo {

    private String taskName;

    private Long startTime;

    private Long endTime;

    private String empId;

    private Integer prjId;

    private Integer status;
}
