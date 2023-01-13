package com.cp.zf.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cp.zf.entity.TRykJbxx;

public interface TRykJbxxService extends IService<TRykJbxx> {

    /**
     * 上传设备卡信息
     * @return
     * @throws Exception
     */
    boolean uploadDeviceCard()throws Exception;
}
