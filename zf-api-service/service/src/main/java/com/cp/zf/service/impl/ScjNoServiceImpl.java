package com.cp.zf.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cp.util.RedisUtil;
import com.cp.zf.entity.ScjNo;
import com.cp.zf.mapper.ScjNoMapper;
import com.cp.zf.output.ScjNoOutput;
import com.cp.zf.service.ScjNoService;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
@Service
public class ScjNoServiceImpl extends ServiceImpl<ScjNoMapper, ScjNo> implements ScjNoService {

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public ScjNoOutput byScj(String scj) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("scj", scj);
        ScjNo scjNo = this.getOne(queryWrapper);

        ScjNoOutput scjNoOutput = new ScjNoOutput();
        if (null != scjNo) {
            try {
                BeanUtils.copyProperties(scjNoOutput, scjNo);
                scjNoOutput.setGd_status(redisUtil.getCnStr("M-" + scjNoOutput.getCIp()));

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return scjNoOutput;
    }
}
