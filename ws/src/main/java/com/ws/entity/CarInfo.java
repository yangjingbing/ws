package com.ws.entity;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class CarInfo {
    private String cardid;
    private String carnum;
    private String description;
    private Timestamp dt;
//    private Integer pasted;
//    private Double x;
//    private String time;


}
