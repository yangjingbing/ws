package com.cp.zf.service;

        import com.baomidou.mybatisplus.extension.service.IService;
        import com.cp.zf.entity.ScjNo;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @since 2021-03-14
 */
public interface ScjNoService extends IService<ScjNo> {

    ScjNo byScj(String scj);
}
