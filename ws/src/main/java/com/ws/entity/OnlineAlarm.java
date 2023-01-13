package com.ws.entity;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class OnlineAlarm {
    private Integer id;
    private String name;
    private String cardId;
    private String workType;
    private Timestamp dtime;
    private Integer alarmType;
    private String alarmTypeName;
    private String areaId;
    private String areaName;
    private Integer layerId;
    private Integer sourceStationId;

    /*public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getWorkType() {
        return workType;
    }

    public void setWorkType(String workType) {
        this.workType = workType;
    }

    public Timestamp getDtime() {
        return dtime;
    }

    public void setDtime(Timestamp dtime) {
        this.dtime = dtime;
    }

    public Integer getAlarmType() {
        return alarmType;
    }

    public void setAlarmType(Integer alarmType) {
        this.alarmType = alarmType;
    }

    public String getAlarmTypeName() {
        return alarmTypeName;
    }

    public void setAlarmTypeName(String alarmTypeName) {
        this.alarmTypeName = alarmTypeName;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public Integer getLayerId() {
        return layerId;
    }

    public void setLayerId(Integer layerId) {
        this.layerId = layerId;
    }

    public Integer getSourceStationId() {
        return sourceStationId;
    }

    public void setSourceStationId(Integer sourceStationId) {
        this.sourceStationId = sourceStationId;
    }*/
}
