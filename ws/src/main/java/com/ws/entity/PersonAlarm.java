package com.ws.entity;

import lombok.Data;

/**
 * @author isHello
 */
@Data
public class PersonAlarm {
    //主键
    private Integer id;
    //报警人姓名
    private String personName;
    //报警人卡号
    private Integer card;
    //报警事时间
    private String time;
    //报警类型
    private Integer alarmType;
    //车间编号
    private String layerId;
    //车间名称
    private String layerName;
    //经度
    private String x;
    //纬度
    private String y;
    //建筑编号
    private String buildingId;
    //建筑名称
    private String buildingName;
    //报警级别
    private Integer alarmLeavel;
    //报警内容
    private String alarmContent;
    // 处理结果
    private String cljg;
}
