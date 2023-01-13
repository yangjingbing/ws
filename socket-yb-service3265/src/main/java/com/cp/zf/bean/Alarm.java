package com.cp.zf.bean;

import lombok.Data;

/**
 * @author yjb
 * @date 2021/12/7 15:13
 */
@Data
public class Alarm {
    private String mc;
    private Integer cardID;
    private String DT;
    private Integer messageType;
    private String inside_qymcs;
    private Integer sourceStationID;
}
