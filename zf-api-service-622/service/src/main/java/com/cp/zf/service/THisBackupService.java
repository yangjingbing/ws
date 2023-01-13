package com.cp.zf.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cp.zf.entity.THisBackup;

/**
 * <p>
 * 实时位置服务类
 * </p>
 *
 * @author cpgu
 * @since 2021-01-11
 */
public interface THisBackupService extends IService<THisBackup> {

    /**
     * 上报位置
     * @throws Exception
     */
    void uploadPosition() throws Exception;
}
