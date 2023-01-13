package com.cp.zf.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cp.zf.entity.THisBackup;

public interface THisBackupService extends IService<THisBackup> {

    /**
     * 上报位置
     * @throws Exception
     */
    void uploadPosition() throws Exception;
}
