package com.cp.zf.output;

import com.cp.zf.entity.ScjNo;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Description TODO .</br>
 * <></>
 * @Author gu
 * @Date 2021/3/17 1:02
 * @Version 1.0.0
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class ScjNoOutput extends ScjNo {
    private String gd_status;
}
