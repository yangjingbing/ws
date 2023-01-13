package com.ws.mapper;

import com.ws.entity.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

public interface CommonMapper {

    /**
     * 查询所有工种信息
     * @return
     */
    List<WorkType> selectWorkType();

    /**
     * 查询指定工种人数
     * @param id
     * @return
     */
    Integer selectPeopleNumberByWorkType(Integer id);

    /**
     * 查询所有区域信息
     * @return
     */
    List<Region> selectRegion();

    /**
     * 查询指定区域人数
     * @param id
     * @return
     */
    Integer selectPeopleNumberByRegion(Integer id);

    /**
     * 查询指定区域人员坐标
     * @param id
     * @return
     */
    List<PersonPoint> selectPersonPointByRegion(Integer id);
    List<PersonPoint> selectPersonPointByOtherRegion(Integer id);
    List<PersonAlarm> selectPersonAlarm();

    List<AccessRecord> selectAccessRecord();
    /**
     * 三吉利定位推送数据
     * @param startTime
     * @param endTime
     * @return
     */
    List<PushData> pushPositionData(@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")@Param("startTime") Date startTime, @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")@Param("endTime") Date endTime);
    /**
     * 查询历史轨迹
     */
    List<HisTraInfo> selHisTra(@Param("cardId")String cardId, @Param("startTime")String startTime, @Param("endTime")String endTime);
    /**
     * 各地区实时在线人数
     */
    List<OnLinePerson> selOnLinePerson();

    /**
     * 统计总人数
     */
    List<PeoNum> selPeoNum();
    /**
     * 实时报警数据
     * @return
     */
    List<OnlineAlarm> selOnlineAlarm();
    /**
     * 实体实时定位
     * @return
     */
    List<OnlinePeoPoint> selOnlinePeoPoint();
    /**
     * 实体信息列表
     * @return
     */
    List<PersonInfo> selAllPeoInfo();
    /**
     * 设备定位信息
     * @return
     */
    List<ApointLocInfo> selApointLocInfo();
    /**
     * 车辆速度计算
     * @param cardId
     * @param startTime
     * @param endTime
     * @return
     */
    List<CarInfo> calCarSpeed(@Param("cardId")String cardId, @Param("startTime")String startTime, @Param("endTime")String endTime);
    /**
     * 人员卡基本信息
     * @return
     */
    List<RyCardInfo> selRykInfo();
    /**
     * 经纬度坐标信息
     * @return
     */
    List<locJAndW> selLoc();
    /**
     * 获取所有人员信息
     * @return
     */
    List<People> getAll();
    /**
     * 抬杠查询车辆信息
     * @return
     */
    List<CarInfo> selCarInfo();

    /**
     * 不抬杠查询车辆信息
     * @return
     */
    List<CarInfo> selCarInfo2();

    List<People> getAll1();

    //查询所有点位
    List<DevicesInfo> selectDevices();
    //查询指定点位坐标
    List<Point> selectDevicesInfoById(Integer id);


    List<Position> selectPositions();
    List<Point> selectCardById(@Param("cid") Integer cid);

    List<Trajectroy> selectHisTrajectory(@Param("cardId")Integer cardId,@Param("startTime") String startTime, @Param("endTime")String endTime);
    List<Point> selectHisTr(@Param("cardId")Integer cardId, @Param("time")String time);

    List<Barriers> selBarrierInfo();
    List<Point1> selectBarrierInfo(@Param("id") Integer id);

    List<Warnings> selWarnings();

    List<stationid> selStationId();

    /**
     * 三吉利
     * @return
     */
    List<Staff> pushData();

    XTZS selectDm();

    List<PersonPointInfo> selectAllRegion();

    /**
     * 电子围栏报警
     */
    List<Fence> selFenceAlarmInfo();

    /**
     * 隧道出入洞
     */
    List<InOrOutInfo> selInOrOutInfo();

    List<Person> selPersonInfoByCard(@Param("cardId") Integer cardId);

    /**
     * 查询人员卡电量
     * @return
     */
    List<Battery> getBattery();

    /**
     * 基站位置信息
     * @return
     */
    List<JZ_JBXX> getStationPos();

    /**
     *  卡片新增绑定
     * @param ryId
     * @param cardSn
     * @return
     */
    Integer insertRyk(@Param("ryId") Integer ryId, @Param("cardSn") Integer cardSn);

    /**
     * 修改人员卡（即修改绑定）
     * @param ryId
     * @param cardSn
     * @return
     */
    Integer updateRyk(@Param("ryId") Integer ryId, @Param("cardSn") Integer cardSn);

    /**
     * 删除人员卡绑定信息
     * @param ryId
     * @return
     */
    Integer deleteRyk(@Param("ryId") Integer ryId);

    /**
     * 新增网关基站
     * @param gateJzxx
     * @return
     */
    Integer inserGateWay(GateJzxx gateJzxx);

    /**
     * 修改网关基站
     * @param gateJzxx
     * @return
     */
    Integer updGateWay(GateJzxx gateJzxx);

    /**
     * 删除基站
     * @param id
     * @return
     */
    Integer delGateWay(@Param("id") Integer id);

    /**
     * 查询所有网关基站
     * 根据基站id查询
     * @return
     * @param type
     */
    List<GateJzxx> selGateWay(@Param("type") Integer type);
    List<GateJzxx> selGateWayByStatId(@Param("station_id") Integer station_id);

    /**
     * 根据卡号查找信息
     * @param cardSn
     * @return
     */
    List<PersonInfo> selRykInfoByCSn(@Param("cardSn") Integer cardSn);

    /**
     * 新增人员
     * @param ryJbxx
     * @return
     */
    int inserRyJbxx(RyJbxx ryJbxx);

    /**
     * 修改人员信息
     * @param ryJbxx
     * @return
     */
    int updateRy(RyJbxx ryJbxx);

    /**
     * 删除人员信息
     * @param id
     * @return
     */
    int delRyJbxx(@Param("id") Integer id);

    /**\
     * 和利时上传省平台接口
     * @return
     */
    List<PersonPoint> selectAllRegions();
}
