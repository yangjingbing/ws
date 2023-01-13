package com.ws.entity;

import lombok.Data;

import java.util.List;

/**
 * @author isHello
 */
@Data
public class Region {

    // 区域ID
    private Integer id;
    // 区域编号
    private String number;
    // 区域名称
    private String name;
    // 区域总人数
  private Integer sum;
    // 区域所有人员信息
 private List<PersonPoint> personPoints;
//    private Region2 region2;
}
