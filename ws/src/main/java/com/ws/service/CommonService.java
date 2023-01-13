package com.ws.service;

import com.ws.entity.GateJzxx;
import com.ws.entity.RyJbxx;
import com.ws.until.RestUntil;
import com.ws.until.RestUntil1;
import com.ws.until.TagInfo;

import java.util.Map;

public interface CommonService {
    RestUntil selWorkType();

    RestUntil selRegionPersonPoint();

    RestUntil selectPersonAlarm();

    RestUntil selectAccessRecord();
    /**
     * 查询所有工种
     * @return
     */
    RestUntil selWowrkTypeMC();
    /**
     * 历史轨迹查询
     * @return
     */
    RestUntil selHisTra(String cardId,String startTime,String endTime);
    /**
     * 各地区实时在线人数
     * @return
     */
    RestUntil selOnLinePerson();
    /**
     * 统计总人数
     * @return
     */
    RestUntil selPeoNum();
    /**
     * 实时报警数据
     * @return
     */
    RestUntil selOnlineAlarm();
    /**
     * 实体实时定位
     * @return
     */
    RestUntil selOnlinePeoPoint();
    /**
     * 实体信息
     * @return
     */
    RestUntil selAllPeoInfo();
    /**
     * 设备位置信息
     * @return
     */
    RestUntil selApointLocInfo();
    /**
     * 车辆速度计算
     * @return
     */
    //RestUntil calCarSpeed(String cardId,String startTime,String endTime);
    /**
     * 人员卡基本信息
     * @return
     */
    RestUntil selRykInfo();
    /**
     * 人员经纬度数据
     * @return
     */
    RestUntil selLoc();
    /**
     * 获取所有人员信息
     * @return
     */
    RestUntil getAll();
    /**
     * 抬杠信息
     * @return
     */
    RestUntil selCarInfo();

    /**
     * 不抬杠信息
     * @return
     */
    RestUntil selCarInfo1();

    TagInfo getAll1();

    RestUntil1 getDevicesInfo();

    RestUntil1 getRealtimePos();

    RestUntil1 getTrajectory(Integer cardId,String startTime,String endTime);

    RestUntil1 getBarrierInfo();

    RestUntil getWarnings();

    RestUntil getStaionId();

    Map selectDm();

    /**
     * 获取电子围栏报警
     * @return
     */
    RestUntil selFenceAlarmInfo();

    /**
     * 获取进出洞人员信息
     * @return
     */
    RestUntil selInOrOutInfo();

    /**
     * 根据卡号获取人员部分信息
     * @return
     * @param cardId
     */
    RestUntil selPersonInfoByCard(Integer cardId);

    /**
     * 获取人员卡电量信息
     * @return
     */
    RestUntil getBattery();
    /**
     * 基站经纬度
     * @return
     */
    RestUntil getStationPos();

    /**
     * 新增定位卡
     * @return
     */
    RestUntil insertRyk(Integer ryId,Integer cardSn);
    /**
     * 修改定位卡
     * @param ryId
     * @param cardSn
     * @return
     */
    RestUntil updateRyk(Integer ryId, Integer cardSn);
    /**
     * 删除定位卡
     * @param ryId
     * @return
     */
    RestUntil deleteRyk(Integer ryId);
    /**
     * 新增网关基站
     * @param gateJzxx
     * @return
     */
    RestUntil inserGateWay(GateJzxx gateJzxx);
    /**
     *
     * @param gateJzxx
     * @return
     */
    RestUntil updateGateWay(GateJzxx gateJzxx);

    /**
     * 根据主键id删除基站
     * @param id
     * @return
     */
    RestUntil delGateWay(Integer id);

    /**
     * 查询所有网关基站
     * @return
     */
    RestUntil selGateWay(Integer type,Integer station_id);

    /**
     * 根据卡号查找相关信息
     * @param cardSn
     * @return
     */
    RestUntil selRykInfoByCSn(Integer cardSn);

    /**
     * 新增人员信息
     * @param ryJbxx
     * @return
     */
    RestUntil inserRyJbxx(RyJbxx ryJbxx);

    /**
     * 修改人员信息
     * @param ryJbxx
     * @return
     */
    RestUntil updateRy(RyJbxx ryJbxx);

    /**
     * 删除人员信息
     * @param id
     * @return
     */
    RestUntil delRyJbxx(Integer id);
}
