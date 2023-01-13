package com.ws.entity;

import lombok.Data;

@Data
public class Warnings {
    private String name;
    private String cid;
    private Integer warningType;
    private String warningtime;
    private Integer workshopid;
    private String worknum;
    private String barrierid;
}
