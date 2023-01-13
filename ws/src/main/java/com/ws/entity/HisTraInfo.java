package com.ws.entity;

import lombok.Data;

@Data
public class HisTraInfo {
    private String cardid;
    private String time;
    private String x;
    private String y;
    private String layerName;

    /*public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    public String getCardid() {
        return cardid;
    }

    public void setCardid(String cardid) {
        this.cardid = cardid;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }*/

//    public String getLayerName() {
//        return layerName;
//    }
//
//    public void setLayerName(String layerName) {
//        this.layerName = layerName;
//    }
}
