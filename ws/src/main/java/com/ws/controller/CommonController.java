package com.ws.controller;

import com.ws.entity.GateJzxx;
import com.ws.entity.RyJbxx;
import com.ws.service.CommonService;
import com.ws.until.RestUntil;
import com.ws.until.RestUntil1;
import com.ws.until.TagInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

//import static com.ws.until.ConvertUtil.xy2lonlat;

@RestController
public class CommonController {

    @Resource
    private CommonService service;

    @RequestMapping("/")
    public String hello() {
        return "人员定位接口";
    }

    @RequestMapping("/selWorkType")
    public RestUntil selWorkType() {
        return service.selWorkType();
    }

    @RequestMapping("/selRegionPersonPoint")
    public RestUntil selRegionPersonPoint() {
        return service.selRegionPersonPoint();
    }

    @RequestMapping("/selectPersonAlarm")
    public RestUntil selectPersonAlarm(){
        return service.selectPersonAlarm();
    }

    @RequestMapping("/selAccessRecord")
    public RestUntil selectAccessRecord(){
        return service.selectAccessRecord();
    }

    /**
     * 查询所有人员
     * @return
     */
    @RequestMapping("/selAllStaff")
    public RestUntil selAllStaff(){
        return service.selectAccessRecord();
    }

    /**
     * 查询新增人员
     * @return
     */
    @RequestMapping("/selAddStaff")
    public RestUntil selAddStaff(){
        return service.selectAccessRecord();
    }

    /**
     * 查询修改人员
     * @return
     */
    @RequestMapping("/selModifyStaff")
    public RestUntil selModifyStaff(){
        return service.selectAccessRecord();
    }

    /**
     * 查询所有工种
     * @return
     */
    @RequestMapping("/selWowrkTypeMC")
    public RestUntil selWowrkTypeMC(){ return service.selWowrkTypeMC(); }
    /**
     * 历史轨迹查询
     * @return
     */
    @RequestMapping("/selHisTra")
    public RestUntil selHisTra(String cardId,String startTime,String endTime){ return service.selHisTra(cardId,startTime,endTime); }
    /**
     * 各地区实时在线人数
     * @return
     */
    @RequestMapping("/selOnLinePerson")
    public RestUntil selOnLinePerson(){ return service.selOnLinePerson();}
    /**
     * 统计总人数
     * @return
     */
    @RequestMapping("/selPeoNum")
    public RestUntil selPeoNum(){ return service.selPeoNum(); }
    /**
     * 实时报警数据
     * @return
     */
    @RequestMapping("/selOnlineAlarm")
    public RestUntil selOnlineAlarm(){ return service.selOnlineAlarm(); }
    /**
     * 实体实时定位数据
     * @return
     */
    @RequestMapping("/selOnlinePeoPoint")
    public RestUntil selOnlinePeoPoint(){ return service.selOnlinePeoPoint(); }
    /**
     * 所有实体信息列表
     * @return
     */
    @RequestMapping("/api/v2/perInfo")
    public RestUntil selAllPeoInfo(){ return service.selAllPeoInfo(); }
    /**
     * 设备位置信息
     */
    @RequestMapping("/api/v2/loc")
    public RestUntil selApointLocInfo(){return service. selApointLocInfo();}
    /**
     * 车辆速度计算
     */
    /*@RequestMapping("/carSpeed")
    public RestUntil CalCarSpeed(String cardId,String startTime,String endTime){return service.calCarSpeed(cardId,startTime,endTime);}*/
    /**
     * 人员卡基本信息
     */
    @RequestMapping("/api/v2/RykInfo")
    public RestUntil selRykInfo(){return service.selRykInfo();}
    /**
     * 人员经纬度数据
     */
    @RequestMapping("/selLoc")
    public RestUntil selLoc(){return service.selLoc();}
    /**
     * 获取所有人员信息
     */
    @RequestMapping("/getAll")
    public RestUntil getAll(){return service.getAll();}
    /**
     * 抬杠信息：卡号和车牌号
     */
    @RequestMapping("/selInfoByPt1")
    public RestUntil selCarInfo(){return service.selCarInfo();}

    /**
     * 不抬杠信息：卡号和车牌号
     */
    @RequestMapping("/selInfoByPt2")
    public RestUntil selCarInfo1(){return service.selCarInfo1();}

    @RequestMapping("/getAll1")
    public TagInfo getAll1(){return service.getAll1();}

    /**
     * 定位器以及各种类型点位接口
     * @return
     */
    @RequestMapping("/info/getDevicesInfo")
    public RestUntil1 getDevicesInfo(){return service.getDevicesInfo();}

    /**
     * 获取信号卡实时位置
     * @return
     */
    @RequestMapping("/info/getRealtimePos")
    public RestUntil1 getgetRealtimePos(){return service.getRealtimePos();}

    /**
     * 人员历史轨迹
     * @return
     */
    @RequestMapping("/info/getTrajectory")
    public RestUntil1 getTrajectory(Integer cardId,String startTime,String endTime){return service.getTrajectory(cardId,startTime,endTime);}

    /**
     * 获取项目电子围栏点位信息。
     * @return
     */
    @RequestMapping("/info/getBarrierInfo")
    public RestUntil1 getBarrierInfo(){return service.getBarrierInfo();}

    /**
     *
     * @return
     */
    @RequestMapping("/warn/getWarnings")
    public RestUntil getWarnings(){return service.getWarnings();}

    @RequestMapping("/getStationId")
    public RestUntil getStationId(){return service.getStaionId();}

    /**
     * 获取电子围栏点位报警信息
     */
    @RequestMapping("/selFenceAlarmInfo")
    public  RestUntil selFenceAlarmInfo(){return service.selFenceAlarmInfo();}

    /**
     * 获取隧道进出洞信息接口
     */
    @RequestMapping("/selInOrOutInfo")
    public  RestUntil selInOrOutInfo() {return service.selInOrOutInfo();}

    /**
     * 根据卡号获取人员部分信息
     */
    @RequestMapping("/selPersonInfoByCard")
    public  RestUntil selPersonInfoByCard(Integer cardId) {return service.selPersonInfoByCard(cardId);}

    /**
     * 获取人员卡电量信息
     */
    @RequestMapping("/getBattery")
    public RestUntil getBattery(){
        return service.getBattery();
    }

    /**
     * 基站经纬度
     * @return
     */
    @RequestMapping("/getStationPos")
    public RestUntil getStationPos(){
        return service.getStationPos();
    }
    /**
     * 新增定位卡
     */
    @RequestMapping("/insertRyk")
    public RestUntil insertRyk(Integer ryId,Integer cardSn) { return service.insertRyk(ryId,cardSn);}

    /**
     * 修改定位卡卡号
     */
    @RequestMapping("/updateRyk")
    public RestUntil updateRyk(Integer ryId,Integer cardSn) {return service.updateRyk(ryId,cardSn);}
    /**
     * 删除定位卡
     */
    @RequestMapping("/deleteRyk")
    public RestUntil deleterRyk(Integer ryId) {return service.deleteRyk(ryId);}
    /**
     * 新增网关基站
     */
    @RequestMapping("/inserGateWay")
    public RestUntil inserGateWay(@RequestBody GateJzxx gateJzxx) {return service.inserGateWay(gateJzxx);}
    /**
     * 编辑网关基站
     */
    @RequestMapping("/updateGateWay")
    public RestUntil updateGateWay(@RequestBody GateJzxx gateJzxx){return service.updateGateWay(gateJzxx);}

    /**
     * 根据id号删除基站
     * @param id
     * @return
     */
    @RequestMapping("/deleteGateWay")
    public RestUntil deleteGateWay(Integer id){return service.delGateWay(id);};

    /**
     * 查询所有网关基站
     */
    @RequestMapping("/selGateWay")
    public RestUntil selGateWay(Integer type,Integer station_id){return service.selGateWay(type,station_id);};

    /**
     * 根据卡号查找相关信息
     */
    @RequestMapping("/selRykInfoByCSn")
    public RestUntil selRykInfoByCSn(Integer cardSn){return service.selRykInfoByCSn(cardSn);}
    /**
     * 新增人员信息
     */
    @RequestMapping("/inserRy")
    public RestUntil inserRyJbxx(@RequestBody RyJbxx ryJbxx){return service.inserRyJbxx(ryJbxx);}
    /**
     * 修改人员信息
     */
    @RequestMapping("/updateRy")
    public RestUntil updRyJbxx(@RequestBody RyJbxx ryJbxx){return service.updateRy(ryJbxx);}
    /**
     * 删除人员信息
     */
    @RequestMapping("deleteRy")
    public RestUntil delRyJbxx(Integer id){return service.delRyJbxx(id);}
}
