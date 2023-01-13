package com.ws.entity;

import lombok.Data;

/**
 * @author isHello
 */
@Data
public class PersonPoint {
    private Integer id;
    private String card;
    private String name;
    private Integer sex;
    private String position;
    private String workType;
    private String x;
    private String y;
    private String time;
    private Integer floor;
    private String stationId;
    // 地铁新增
//    private Double speed;
//    private Integer type;
    /**
     * 和利时接口
     */
    /* private String id;
    private String card_no;
    private String name;
    private Integer status;
    private String longitude;
    private String latitude;
    private String time_stamp;
    private Integer floor_no;
    private String company_code;*/
}
