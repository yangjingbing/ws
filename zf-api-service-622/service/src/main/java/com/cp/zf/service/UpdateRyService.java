package com.cp.zf.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cp.zf.entity.UpdateRyxx;
import com.cp.zf.output.RyxxOutput;

/**
 * @author yjb
 * @date 2021/7/16 9:47
 */
public interface UpdateRyService extends IService<UpdateRyxx> {

    RyxxOutput byUuid(String uuid,String zp);
}
