package com.ws.entity;

import lombok.Data;

/**
 * @author isHello
 */
@Data
public class PushData {
    private String id;
    private String type;
    private String x;
    private String y;
    private String z;
    private String card;
    private String time;
    private String info;
    private String battery;
    private String floor;
}
