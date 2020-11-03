package com.gunners.epes.entity.vo;

import lombok.Data;

@Data
public class ProjectVo extends PageVo {

    private String prjName;

    private Long startTime;

    private Long endTime;

    private Integer status;
}
