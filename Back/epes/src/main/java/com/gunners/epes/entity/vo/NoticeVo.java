package com.gunners.epes.entity.vo;

import lombok.Data;

@Data
public class NoticeVo extends PageVo {

    private String title;

    private Long startTime;

    private Long endTime;

}
