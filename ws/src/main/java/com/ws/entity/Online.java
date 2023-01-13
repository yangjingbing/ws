package com.ws.entity;

import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Data
public class Online {
    private Date t;
    private List<Position> position;
}
