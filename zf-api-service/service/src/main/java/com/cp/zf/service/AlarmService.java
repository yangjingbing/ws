package com.cp.zf.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cp.zf.entity.Alarm;

/**
 * <p>
 *  预警服务类
 * </p>
 *
 * @author cpgu
 * @since 2021-01-12
 */
public interface AlarmService extends IService<Alarm> {

    Integer byCardNo(String cardNo);
}
