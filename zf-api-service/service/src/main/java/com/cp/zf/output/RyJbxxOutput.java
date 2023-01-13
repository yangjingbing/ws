package com.cp.zf.output;

import com.cp.zf.entity.RyJbxx;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
@EqualsAndHashCode(callSuper = true)
@Data
public class RyJbxxOutput extends RyJbxx {

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private String carnum;

    private String is_have;

    private String qymc;

    private String gd_status;

    private Integer V;
}
