package com.cp.zf.output;

import com.cp.zf.entity.RyJbxx;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Description TODO .</br>
 * <></>
 * @Author gu
 * @Date 2021/3/12 16:14
 * @Version 1.0.0
 **/
@Data
public class RyJbxxOutput extends RyJbxx {

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private String carnum;

    private String is_have;

    private String qymc;

    private String gd_status;
}
