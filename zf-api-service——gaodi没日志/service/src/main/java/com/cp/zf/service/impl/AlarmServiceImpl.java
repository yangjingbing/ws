package com.cp.zf.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cp.zf.entity.Alarm;
import com.cp.zf.mapper.AlarmMapper;
import com.cp.zf.service.AlarmService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  预警服务实现类
 * </p>
 *
 * @author cpgu
 * @since 2021-01-12
 */
@Service
public class AlarmServiceImpl extends ServiceImpl<AlarmMapper, Alarm> implements AlarmService {

    @Override
    public Integer byCardNo(String cardNo) {
        return this.baseMapper.byCardNo(cardNo);
    }
}
