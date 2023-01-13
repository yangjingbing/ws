package com.ws.controller;

import com.ws.service.StaffService;
import com.ws.until.RestUntil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author isHello
 */
@RestController
public class StaffController {

    @Resource
    private StaffService staffService;

    /**
     * 获取员工信息-三吉利
     */
    @RequestMapping("/getStaffByMark")
    public RestUntil getStaffByMark(Integer mark){
        return staffService.getStaffByMark(mark);
    }

    @RequestMapping("/getStaff")
    public RestUntil getStaff(){
        return staffService.getStaff();
    }
}
