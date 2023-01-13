package com.ws.entity;

import lombok.Data;

import java.util.List;

@Data
public class DevicesInfo {
    private Integer id;
    private Integer workshopid;
    private List<Point> point;

}
