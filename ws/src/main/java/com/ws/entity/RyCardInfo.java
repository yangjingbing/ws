package com.ws.entity;

import lombok.Data;


@Data
public class RyCardInfo {
    private Integer id;
    private String cardId;
    private Integer ryId;
    private String status;
    private String inputTime;
    private String inputPerson;
}
