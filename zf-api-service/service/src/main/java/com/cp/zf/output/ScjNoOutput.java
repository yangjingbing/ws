package com.cp.zf.output;

import com.cp.zf.entity.ScjNo;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ScjNoOutput extends ScjNo {
    private String gd_status;
}
