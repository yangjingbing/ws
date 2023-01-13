package com.ws.entity;

import lombok.Data;

import java.util.List;

@Data
public class Position {
    private Integer cid;
    private String dt;
    private List<Point> points;
}
