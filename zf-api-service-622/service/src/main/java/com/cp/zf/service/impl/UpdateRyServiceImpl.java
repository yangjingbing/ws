package com.cp.zf.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cp.zf.entity.ScjNo;
import com.cp.zf.entity.UpdateRyxx;
import com.cp.zf.mapper.RyJbxxMapper;
import com.cp.zf.mapper.UpdateRyxxMapper;
import com.cp.zf.output.RyxxOutput;
import com.cp.zf.service.UpdateRyService;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;

/**
 * @author yjb
 * @date 2021/7/16 9:59
 */
@Service
public class UpdateRyServiceImpl extends ServiceImpl<UpdateRyxxMapper, UpdateRyxx> implements UpdateRyService {


    @Override
    public RyxxOutput byUuid(String uuid,String zp) {

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("uuid", uuid);
        UpdateRyxx updateRyxx = this.getOne(queryWrapper);
        updateRyxx.setZp(zp);

        RyxxOutput ryxxOutput = new RyxxOutput();
        if (null != ryxxOutput) {
            try {
                BeanUtils.copyProperties(ryxxOutput, updateRyxx);
                ryxxOutput.setZp(updateRyxx.getZp());

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return ryxxOutput;
    }


}
