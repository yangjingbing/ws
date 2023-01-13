package com.ws.mapper;

import com.ws.entity.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author isHello
 */
public interface StaffMapper {

    /**
     * 获取所有员工信息-三吉利
     * @param mark
     * @return
     */
    List<Staff> getStaffByMark(Integer mark);

    /**
     * 根据ID修改人员信息
     * @param staffId
     * @return
     */
    Integer updateStaffById(Integer staffId);

    List<StaffNew> getStaff();

    List<Staff> pushData();
    List<StaffNew> getStaff1(@Param("mc") String mc);

    Integer updateStaffById1(@Param("id") Integer id);
    List<Staff1> getStaff2();

    List<Staff> pushData1(@Param("mc") String mc);

    Integer updateStaffById2(@Param("id") Integer id);

    SendZT getSendZT(@Param("id") Integer id);

    Integer updateStaffSendZT(@Param("id") Integer id);

    Integer updateStaffZT(@Param("id") Integer id);

    Integer updateStaffSendZT1(@Param("id") Integer id);

    Integer updateStaffSendZT2(@Param("id") Integer id);

    SendZT getSendZTBD(@Param("id") Integer id);

    Integer updateStaffSendZTBD(@Param("id") Integer id);

    SendZT getSendZTJB(@Param("id") Integer id);

    Integer updateStaffSendZTJB(@Param("id") Integer id);

    List<StaffPersonAlarm> pushAlarmData();

    SendAlarmZT getZtById(@Param("id") Integer id);

    Integer updateStaffAlarmSendZt(@Param("id") Integer id);
}
