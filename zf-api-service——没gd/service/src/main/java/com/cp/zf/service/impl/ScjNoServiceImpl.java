package com.cp.zf.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cp.zf.entity.ScjNo;
import com.cp.zf.mapper.ScjNoMapper;
import com.cp.zf.service.ScjNoService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author cpgu
 * @since 2021-03-14
 */
@Service
public class ScjNoServiceImpl extends ServiceImpl<ScjNoMapper, ScjNo> implements ScjNoService {

    @Override
    public ScjNo byScj(String scj) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("scj", scj);
        return this.getOne(queryWrapper);
    }
}
