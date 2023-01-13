package com.ws.entity;

import lombok.Data;

import java.util.List;

@Data
public class Barriers {
    private Integer barrierid;
    private String barriernm;
    private Integer workshopid;
    private List<Point1> points;
}
