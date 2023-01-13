package com.ws.entity;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class locJAndW {
    private Integer id;
    private String macNo;
    private Timestamp time;
    private String lng;
    private String lat;
    private String name;
    private String Mapcode;

}
