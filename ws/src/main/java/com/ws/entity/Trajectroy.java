package com.ws.entity;

import lombok.Data;

import java.util.List;

@Data
public class Trajectroy {
    private Integer cid;
    private String t;
    private List<Point> points;

    /*public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public String getT() {
        return t;
    }

    public void setT(String t) {
        this.t = t;
    }

    public List<Point> getPoints() {
        return points;
    }

    public void setPoints(List<Point> points) {
        this.points = points;
    }*/
}
