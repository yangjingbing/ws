package com.ws.entity;

import lombok.Data;

@Data
public class ApointLocInfo {
    private String id;
    private String ryName;
    private String time;
    private String lng;//经度
    private String lat;//纬度
    private Integer layer;
//    private Integer area;
    private String status;

}
