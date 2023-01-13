package com.ws.entity;

import lombok.Data;

/**
 * @author yjb
 * @date 2022/6/16 18:06
 */
@Data
public class GateJzxx {
    private Integer id;
    private String mc;
    private Integer type;
    private Integer station_id;
    private Integer layer_id;
    private Double geo_x;
    private Double geo_y;
    private Double geo_z;
    private String ip;
}
