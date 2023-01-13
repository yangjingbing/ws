package com.cp.zf.output;

import com.cp.zf.entity.RyJbxx;
import com.cp.zf.entity.UpdateRyxx;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author yjb
 * @date 2021/7/15 10:11
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class RyxxOutput extends UpdateRyxx {
    private String zp;
}
