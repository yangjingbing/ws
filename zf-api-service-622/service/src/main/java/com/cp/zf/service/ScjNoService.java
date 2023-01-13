package com.cp.zf.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cp.zf.entity.ScjNo;
import com.cp.zf.output.ScjNoOutput;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author cpgu
 * @since 2021-03-14
 */
public interface ScjNoService extends IService<ScjNo> {

    ScjNoOutput byScj(String scj);
}
