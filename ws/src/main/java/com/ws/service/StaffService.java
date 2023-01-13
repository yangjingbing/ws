package com.ws.service;

import com.ws.until.RestUntil;

/**
 * @author isHello
 */
public interface StaffService {
    /**
     * 获取员工信息-三吉利
     * @param mark
     * @return
     */
    RestUntil getStaffByMark(Integer mark);


    RestUntil getStaff();
}
