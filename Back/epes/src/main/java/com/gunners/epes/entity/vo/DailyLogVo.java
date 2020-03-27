package com.gunners.epes.entity.vo;

import lombok.Data;

@Data
public class DailyLogVo extends PageVo {

    private Long startTime;

    private Long endTime;

    private String empId;

    private Integer status;
}
