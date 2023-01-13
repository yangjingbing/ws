package com.ws.entity;

import lombok.Data;

/**
 * @author isHello
 */
@Data
public class AccessRecord {
    private int id;
    private String inOutStatus;
    private String cardNo;
    private String fullName;
    private String sex;
    private String workType;
    private String inTime;
    private String ouTime;
}
