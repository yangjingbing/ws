package com.cp.zf.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cp.zf.entity.TCarInfo;
import com.cp.zf.mapper.TCarInfoMapper;
import com.cp.zf.service.TCarInfoService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 车辆基本信息 服务实现类
 * </p>
 *
 * @since 2021-02-18
 */
@Service
public class TCarInfoServiceImpl extends ServiceImpl<TCarInfoMapper, TCarInfo> implements TCarInfoService {

}
