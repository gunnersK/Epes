package com.gunners.epes.entity.vo;

import lombok.Data;

@Data
public class PrjTaskVo extends PageVo {

    private String taskName;

    private Integer prjId;

    private Long startTime;

    private Long endTime;

    private Integer status;
}
