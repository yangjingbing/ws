package com.ws.entity;

import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;

@Data
public class OnlinePeoPoint {
    private Integer areaId;
    private String dataTime;//定位时间
    private String empName;//姓名
    private String empNum;//工号
    private String workType;//人员类型
    private Integer layerId;
    private Integer ryCardId;
    private String x;
    private String y;
    private String stationId;
}
