package com.ws.service.impl;

import com.alibaba.fastjson.JSON;
import com.ws.entity.*;
import com.ws.geodetic.ConUtil;
import com.ws.mapper.CommonMapper;
import com.ws.service.CommonService;
import com.ws.until.*;
import com.ws.until.TagInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
public class CommonServiceImpl implements CommonService {

    @Autowired
    private CommonMapper commonMapper;


    @Override
    public RestUntil selWorkType() {
        RestUntil restUntil = new RestUntil();
        try {
            List<WorkType> workTypes = commonMapper.selectWorkType();
            List<WorkType> list = new ArrayList<>();
            if (workTypes != null && workTypes.size() > 0) {
                for (int i = 0; i < workTypes.size(); i++) {
                    Integer id = workTypes.get(i).getId();
                    String name = workTypes.get(i).getName();
                    if (name == null && "".equals(name)) {
                        restUntil.setStatus("200");
                        restUntil.setMsg("请求接口没有数据");
                    } else {
                        Integer peopleNumber = commonMapper.selectPeopleNumberByWorkType(id);
                        WorkType workType = new WorkType();
                        workType.setId(workTypes.get(i).getId());
                        workType.setName(workTypes.get(i).getName());
                        workType.setNumber(peopleNumber);
                        list.add(workType);
                        restUntil.setData(list);
                        restUntil.setStatus("200");
                        restUntil.setMsg("请求接口成功");
                    }
                }
            } else {
                restUntil.setStatus("200");
                restUntil.setMsg("请求接口没有数据");
            }
        } catch (Exception e) {
            restUntil.setStatus("500");
            restUntil.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return restUntil;
    }

   /* @Override
    public RestUntil selRegionPersonPoint() {
        RestUntil restUntil = new RestUntil();
        List<Region> list = new ArrayList<>();
        try {
            List<Region> regions = commonMapper.selectRegion();
            if (regions != null && regions.size() > 0) {
                for (int i = 0; i < regions.size(); i++) {
                    Region region1 = regions.get(i);
                    Region region = new Region();
                    Integer id = region1.getId();
                    region.setId(id);
                    region.setNumber(region1.getNumber());
                    region.setName(region1.getName());
                    Integer integer = commonMapper.selectPeopleNumberByRegion(id);
                    region.setSum(integer);
                    if(id==2){
                        List<PersonPoint> personPoints = commonMapper.selectPersonPointByRegion(id);
                      for (PersonPoint info : personPoints) {
                           ConvertUtil convertUtil = new ConvertUtil();
                           double[] lanxy = convertUtil.xy2lonlat(Double.parseDouble(info.getX()),Double.parseDouble(info.getY()),this.selectDm());
                           info.setX(String.valueOf(lanxy[0]));
                           info.setY(String.valueOf(lanxy[1]));
                        }

                        region.setPersonPoints(personPoints);
                    }else {
                        List<PersonPoint> personPoints = commonMapper.selectPersonPointByOtherRegion(id);
                        for (PersonPoint info : personPoints) {
                            ConvertUtil convertUtil = new ConvertUtil();
                            double[] lanxy = convertUtil.xy2lonlat(Double.parseDouble(info.getX()),Double.parseDouble(info.getY()),this.selectDm());
                            info.setX(String.valueOf(lanxy[0]));
                            info.setY(String.valueOf(lanxy[1]));
                        }
                        region.setPersonPoints(personPoints);
                    }
                    list.add(region);
                    restUntil.setStatus("200");
                    restUntil.setMsg("请求接口成功");
                }
                restUntil.setData(list);
            } else {
                restUntil.setStatus("200");
                restUntil.setMsg("请求接口没有数据");
            }
        } catch (Exception e) {
            restUntil.setStatus("500");
            restUntil.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return restUntil;
    }*/

    @Override
    public RestUntil selectPersonAlarm() {

        RestUntil restUntil = new RestUntil();
        try {
            List<PersonAlarm> personAlarms = commonMapper.selectPersonAlarm();
            if (personAlarms != null && personAlarms.size() > 0) {
                for (PersonAlarm personAlarm : personAlarms) {
                    Integer alarmType = personAlarm.getAlarmType();
                    String personName = personAlarm.getPersonName();
                    Integer card = personAlarm.getCard();
                    String layerName = personAlarm.getLayerName();
                   /* ConvertUtil convertUtil = new ConvertUtil();
                    double[] lanxy = convertUtil.xy2lonlat(Double.parseDouble(personAlarm.getX()),Double.parseDouble(personAlarm.getY()));
//                    double[] lanxy = convertUtil.xy2lonlat(Double.parseDouble(personAlarm.getX()),Double.parseDouble(personAlarm.getY()));
                    personAlarm.setX(String.valueOf(lanxy[0]));
                    personAlarm.setY(String.valueOf(lanxy[1]));*/
                    switch (alarmType) {
                        //呼救
                        case 10:
                            personAlarm.setAlarmLeavel(3);
                            personAlarm.setAlarmContent(personName + "(" + card + ")静止报警");
                            break;
                        case 1:
                            personAlarm.setAlarmLeavel(1);
                            personAlarm.setAlarmContent(personName + "(" + card + ")呼救报警");
                            break;
                        //低电量
                        case 4:
                            personAlarm.setAlarmLeavel(0);
                            personAlarm.setAlarmContent(personName + "(" + card + ")低电量报警");
                            break;
                        case 3:
                            personAlarm.setAlarmLeavel(2);
                            personAlarm.setAlarmContent(personName+ "(" + card + ")按键取消报警");
                            break;
                        default:
                    }
                }

                restUntil.setData(personAlarms);
                restUntil.setStatus("200");
                restUntil.setMsg("请求接口成功");
            } else {
                restUntil.setData(null);
                restUntil.setStatus("200");
                restUntil.setMsg("请求接口没有数据");
            }
        } catch (Exception e) {
            restUntil.setStatus("500");
            restUntil.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return restUntil;
    }

    @Override
    public RestUntil selectAccessRecord() {
        RestUntil restUntil = new RestUntil();
        try {
            List<AccessRecord> accessRecords = commonMapper.selectAccessRecord();
            if (accessRecords != null && accessRecords.size() > 0) {
                restUntil.setData(accessRecords);
                restUntil.setStatus("200");
                restUntil.setMsg("请求接口成功");
            } else {
                restUntil.setData(accessRecords);
                restUntil.setStatus("200");
                restUntil.setMsg("请求接口没有数据");
            }
        } catch (Exception e) {
            restUntil.setStatus("500");
            restUntil.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return restUntil;
    }

    /**
     * 查询所有工种
     *
     * @return
     */

    @Override
    public RestUntil selWowrkTypeMC() {
        RestUntil restUntil = new RestUntil();
        try {
            List<WorkType> workTypes = commonMapper.selectWorkType();
            if (CollectionUtils.isEmpty(workTypes)) {
                restUntil.setData(workTypes);
                restUntil.setStatus("200");
                restUntil.setMsg("请求接口没有数据");
            } else {
                restUntil.setData(workTypes);
                restUntil.setStatus("200");
                restUntil.setMsg("请求接口成功");
            }
        } catch (Exception e) {
            restUntil.setStatus("500");
            restUntil.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return restUntil;
    }

    /**
     * 历史轨迹
     *
     * @return
     */

    @Override
    public RestUntil selHisTra(String cardId, String startTime, String endTime) {
        RestUntil restUntil = new RestUntil();
        try {
            List<HisTraInfo> hisTra = commonMapper.selHisTra(cardId, startTime, endTime);

            if (CollectionUtils.isEmpty(hisTra)) {
                restUntil.setData(hisTra);
                restUntil.setStatus("200");
                restUntil.setMsg("请求接口没有数据");
            } else {
                /*for (HisTraInfo info : hisTra) {
                    ConvertUtil convertUtil = new ConvertUtil();
                    double[] lanxy = convertUtil.xy2lonlat(Double.parseDouble(info.getX()),Double.parseDouble(info.getY()));
//                    double[] lanxy = convertUtil.xy2lonlat(Double.parseDouble(info.getX()),Double.parseDouble(info.getY()));
                    info.setX(String.valueOf(lanxy[0]));
                    info.setY(String.valueOf(lanxy[1]));
                }*/
                restUntil.setData(hisTra);
                restUntil.setStatus("200");
                restUntil.setMsg("请求接口成功");
            }
        } catch (Exception e) {
            restUntil.setStatus("500");
            restUntil.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return restUntil;
    }

    /**
     * 各地区实时在线人数
     *
     * @return
     */

    @Override
    public RestUntil selOnLinePerson() {
        RestUntil restUntil = new RestUntil();
        try {
            List<OnLinePerson> onLinePerson = commonMapper.selOnLinePerson();
            if (CollectionUtils.isEmpty(onLinePerson)) {
                restUntil.setData(onLinePerson);
                restUntil.setStatus("200");
                restUntil.setMsg("请求接口没有数据");
            } else {
                restUntil.setData(onLinePerson);
                restUntil.setStatus("200");
                restUntil.setMsg("请求接口成功");
            }
        } catch (Exception e) {
            restUntil.setStatus("500");
            restUntil.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return restUntil;
    }

    /**
     * 统计总人数
     *
     * @return
     */

    @Override
    public RestUntil selPeoNum() {
        RestUntil restUntil = new RestUntil();
        try {
            List<PeoNum> peoNum = commonMapper.selPeoNum();
            if (CollectionUtils.isEmpty(peoNum)) {
                restUntil.setData(peoNum);
                restUntil.setStatus("200");
                restUntil.setMsg("请求接口没有数据");
            } else {
                restUntil.setData(peoNum);
                restUntil.setStatus("200");
                restUntil.setMsg("请求接口成功");
            }
        } catch (Exception e) {
            restUntil.setStatus("500");
            restUntil.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return restUntil;
    }

    /**
     * 实时报警数据
     *
     * @return
     */

    @Override
    public RestUntil selOnlineAlarm() {
        RestUntil restUntil = new RestUntil();
        try {
            List<OnlineAlarm> onlineAlarm = commonMapper.selOnlineAlarm();
            if (CollectionUtils.isEmpty(onlineAlarm)) {
                restUntil.setData(onlineAlarm);
                restUntil.setStatus("200");
                restUntil.setMsg("请求接口没有数据");
            } else {
                for (OnlineAlarm alarm : onlineAlarm) {
                    Integer alType = alarm.getAlarmType();
                    if (alType != null && !"".equals(alType)) {
                        switch (alType) {
                            /*case 1:
                                alarm.setAlarmTypeName("呼救报警");
                                break;
                            case 10:
                                alarm.setAlarmTypeName("长期未移动");
                                break;
                            case 4:
                                alarm.setAlarmTypeName("低电量报警");
                                break;
                            case 60:
                                alarm.setAlarmTypeName("超速报警");
                                break;*/
                            case 3:
                                alarm.setAlarmTypeName("按健取消报警");
                                break;
                            case 1:
                                alarm.setAlarmTypeName("呼叫报警");
                                break;
                            case 4:
                                alarm.setAlarmTypeName("低电量报警");
                                break;
                            case 8:
                                alarm.setAlarmTypeName("静止报警");
                                break;
                            case 10:
                                alarm.setAlarmTypeName("静止报警");
                                break;
                            case 60:
                                alarm.setAlarmTypeName("超速报警");
                                break;
                        }
                    }
                }
                restUntil.setData(onlineAlarm);
                restUntil.setStatus("200");
                restUntil.setMsg("请求接口成功");
            }
        } catch (Exception e) {
            restUntil.setStatus("500");
            restUntil.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return restUntil;
    }

    /**
     * 实时定位
     *
     * @return
     */

    @Override
    public RestUntil selOnlinePeoPoint() {
        RestUntil restUntil = new RestUntil();
        try {
            List<OnlinePeoPoint> onLinePerPoint = commonMapper.selOnlinePeoPoint();
            if (CollectionUtils.isEmpty(onLinePerPoint)) {
                restUntil.setData(onLinePerPoint);
                restUntil.setStatus("200");
                restUntil.setMsg("请求接口没有数据");
            } else {
                restUntil.setData(onLinePerPoint);
                restUntil.setStatus("200");
                restUntil.setMsg("请求接口成功");
            }
        } catch (Exception e) {
            restUntil.setStatus("500");
            restUntil.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return restUntil;
    }

    /**
     * 实体信息
     *
     * @return
     */

    @Override
    public RestUntil selAllPeoInfo() {
        RestUntil restUntil = new RestUntil();
        try {
            List<PersonInfo> personInfo = commonMapper.selAllPeoInfo();
            if (CollectionUtils.isEmpty(personInfo)) {
                restUntil.setData(personInfo);
                restUntil.setStatus("200");
                restUntil.setMsg("请求接口没有数据");
            } else {
                restUntil.setData(personInfo);
                restUntil.setStatus("200");
                restUntil.setMsg("请求接口成功");
            }
        } catch (Exception e) {
            restUntil.setStatus("500");
            restUntil.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return restUntil;
    }

    /**
     * 设备定位信息
     *
     * @return
     */

    @Override
    public RestUntil selApointLocInfo() {
        RestUntil restUntil = new RestUntil();
        try {
            List<ApointLocInfo> locInfo = commonMapper.selApointLocInfo();
            if (CollectionUtils.isEmpty(locInfo)) {
                restUntil.setData(locInfo);
                restUntil.setStatus("200");
                restUntil.setMsg("请求接口没有数据");
            } else {
                for (ApointLocInfo alarm : locInfo) {
                    /*ConvertUtil convertUtil = new ConvertUtil();
                    double[] lanxy = convertUtil.xy2lonlat(Double.parseDouble(alarm.getLng()),Double.parseDouble(alarm.getLat()));
//                    double[] lanxy = convertUtil.xy2lonlat(Double.parseDouble(alarm.getLng()),Double.parseDouble(alarm.getLat()));
                    alarm.setLng(String.valueOf(lanxy[0]));
                    alarm.setLat(String.valueOf(lanxy[1]));*/
                    String alType = alarm.getStatus();
                    if (!StringUtils.isEmpty(alType)) {
                        if ("".equals(alType) || alType == null) {
                            alarm.setStatus("0:在线");
                        } else if ("1".equals(alType)) {
                            alarm.setStatus("1:报警");
                        } else {
                            alarm.setStatus("2:离线");
                        }
                    }
                }
//            }
                restUntil.setData(locInfo);
                restUntil.setStatus("200");
                restUntil.setMsg("请求接口成功");

            }
        } catch (Exception e) {
            restUntil.setStatus("500");
            restUntil.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return restUntil;
    }
 /*@Override
    public RestUntil calCarSpeed(String cardId, String startTime, String endTime) {
        RestUntil restUntil = new RestUntil();
        try {
            List<CarInfo> carInfos = commonMapper.calCarSpeed(cardId,startTime,endTime);
            List<CarInfo> sortList =carInfos.stream().sorted(Comparator.comparing(CarInfo::getId)).collect(Collectors.toList());
            CarInfo min = sortList.get(0);
            CarInfo max = sortList.get(sortList.size()-1);
            Double hours = DateUtil.TimeCov(min.getTime(),max.getTime());
            Double distince = (max.getX() - min.getX()) / 1100;
            Double carSpeed = distince / hours;
            restUntil.setData(carSpeed);
        } catch (Exception e) {
            restUntil.setStatus("500");
            restUntil.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return restUntil;
    }
*/

    /**
     * 人员卡基本信息
     *
     * @return
     */

    @Override
    public RestUntil selRykInfo() {
        RestUntil restUntil = new RestUntil();
        try {
            List<RyCardInfo> ryCardInfos = commonMapper.selRykInfo();
            if (CollectionUtils.isEmpty(ryCardInfos)) {
                restUntil.setData(ryCardInfos);
                restUntil.setStatus("200");
                restUntil.setMsg("请求接口没有数据");
            } else {
                restUntil.setData(ryCardInfos);
                restUntil.setStatus("200");
                restUntil.setMsg("请求接口成功");
            }
        } catch (Exception e) {
            restUntil.setStatus("500");
            restUntil.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return restUntil;
    }

    /**
     * 人员经纬度信息
     *
     * @return
     */

    @Override
    public RestUntil selLoc() {
        RestUntil restUntil = new RestUntil();
        try {
            List<locJAndW> locjw = commonMapper.selLoc();
            if (CollectionUtils.isEmpty(locjw)) {
                restUntil.setData(locjw);
                restUntil.setStatus("200");
                restUntil.setMsg("请求接口没有数据");
            } else {
                /*for (locJAndW info : locjw) {
                    ConvertUtil convertUtil = new ConvertUtil();
                    double[] lanxy = convertUtil.xy2lonlat(Double.parseDouble(info.getLng()),Double.parseDouble(info.getLat()));
//                    double[] lanxy = convertUtil.xy2lonlat(Double.parseDouble(info.getLng()),Double.parseDouble(info.getLat()));
                    info.setLng(String.valueOf(lanxy[0]));
                    info.setLat(String.valueOf(lanxy[1]));
                }*/
                restUntil.setData(locjw);
                restUntil.setStatus("200");
                restUntil.setMsg("请求接口成功");
            }

        } catch (Exception e) {
            restUntil.setStatus("500");
            restUntil.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return restUntil;
    }

    /**
     * 获取所有人员信息
     */

    @Override
    public RestUntil getAll() {
        RestUntil restUntil = new RestUntil();
        try {
            List<People> peo = commonMapper.getAll();

            if (CollectionUtils.isEmpty(peo)) {
                restUntil.setData(peo);
                restUntil.setStatus("200");
                restUntil.setMsg("请求接口没有数据");
            } else {
                /*for (People people : peo) {
                   String type = people.getEmployee_type();
                    if (type.equals("1")){
                        people.setEmployee_type("01");
                    }else if (type.equals("2")){
                        people.setEmployee_type("02");
                    } else if (type.equals("3")) {
                        people.setEmployee_type("03");
                    } else if (type.equals("4")){
                        people.setEmployee_type("04");
                    } else if (type.equals("5")){
                        people.setEmployee_type("05");
                    } else if (type.equals("6")){
                        people.setEmployee_type("06");
                    }*/
                restUntil.setData(peo);
                restUntil.setStatus("200");
                restUntil.setMsg("请求接口成功");
                }
        } catch (Exception e) {
            restUntil.setStatus("500");
            restUntil.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return restUntil;
    }

    /**
     * 抬杆信息
     *
     * @return
     */

    @Override
    public RestUntil selCarInfo() {
        RestUntil restUntil = new RestUntil();
        try {
            List<CarInfo> carInfos = commonMapper.selCarInfo();
            if (CollectionUtils.isEmpty(carInfos)) {
                restUntil.setData(carInfos);
                restUntil.setStatus("200");
                restUntil.setMsg("请求接口没有数据");
            } else {
                restUntil.setData(carInfos);
                restUntil.setStatus("200");
                restUntil.setMsg("请求接口成功");
            }
        } catch (Exception e) {
            restUntil.setStatus("500");
            restUntil.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return restUntil;
    }

    /**
     * 不抬杆信息
     *
     * @return
     */

    @Override
    public RestUntil selCarInfo1() {
        RestUntil restUntil = new RestUntil();
        try {
            List<CarInfo> carInfos = commonMapper.selCarInfo2();
            if (CollectionUtils.isEmpty(carInfos)) {
                restUntil.setData(carInfos);
                restUntil.setStatus("200");
                restUntil.setMsg("请求接口没有数据");
            } else {
                restUntil.setData(carInfos);
                restUntil.setStatus("200");
                restUntil.setMsg("请求接口成功");
            }
        } catch (Exception e) {
            restUntil.setStatus("500");
            restUntil.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return restUntil;
    }

    @Override
    public TagInfo getAll1() {

        TagInfo tagInfo = new TagInfo();
        List<People> peo = commonMapper.getAll1();
        for (int i = 0; i < peo.size(); i++) {
            byte[] a = new byte[i];
            if (CollectionUtils.isEmpty(peo)) {
                tagInfo.setHead("json");
                tagInfo.setTenantId("sc1925");
                tagInfo.setNodeId("a1");
                tagInfo.setTimeTage("2019-06-19T06:34:39.819Z");
                tagInfo.setContent(peo);
                a[i] = Byte.parseByte(String.valueOf(tagInfo));
                UdpUtil.UdpSend(a);
            } else {
                tagInfo.setHead("json");
                tagInfo.setTenantId("sc1925");
                tagInfo.setNodeId("a1");
                tagInfo.setTimeTage("2019-06-19T06:34:39.819Z");
                tagInfo.setContent(peo);
                a[i] = Byte.parseByte(String.valueOf(tagInfo));

                UdpUtil.UdpSend(a);
            }
        }
        return tagInfo;
    }

    @Override
    public RestUntil1 getDevicesInfo() {
        RestUntil1 restUntil1 = new RestUntil1();
        List<DevicesInfo> list = new ArrayList<>();
        try {
            List<DevicesInfo> devices = commonMapper.selectDevices();
            if (devices != null && devices.size() > 0) {
                for (int i = 0; i < devices.size(); i++) {
                    DevicesInfo devices1 = devices.get(i);
                    DevicesInfo device = new DevicesInfo();
                    Integer id = devices1.getId();
                    device.setId(id);
                    device.setWorkshopid(devices1.getWorkshopid());
                    List<com.ws.entity.Point> points = commonMapper.selectDevicesInfoById(id);
                   /* for (Point point : points) {
                        ConvertUtil convertUtil = new ConvertUtil();
                        double[] latlng = convertUtil.xy2lonlat(point.getLng(),point.getLat());
//                        double[] latlng = convertUtil.xy2lonlat(point.getLng(),point.getLat());
                        point.setLng(latlng[0]);
                        point.setLat(latlng[1]);
                    }*/

                    device.setPoint(points);


                    list.add(device);
                    restUntil1.setStatus("0");
                    restUntil1.setMsg("请求接口成功");
                }
                restUntil1.setDevices(list);
            } else {
                restUntil1.setStatus("0");
                restUntil1.setMsg("请求接口没有数据");
            }
        } catch (Exception e) {
            restUntil1.setStatus("1");
            restUntil1.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return restUntil1;
    }

    @Override
    public RestUntil1 getRealtimePos() {
        RestUntil1 restUntil1 = new RestUntil1();
        List<Position> list = new ArrayList<>();
        try {
            List<Position> positions = commonMapper.selectPositions();
            if (positions != null && positions.size() > 0) {
                for (int i = 0; i < positions.size(); i++) {
                    Position position1 = positions.get(i);
                    Position posi = new Position();
                    Integer cid = position1.getCid();
                    String dt = position1.getDt();
                    posi.setDt(dt);
                    posi.setCid(cid);
                    List<com.ws.entity.Point> points = commonMapper.selectCardById(cid);
                    /*for (Point point : points) {
                        ConvertUtil convertUtil = new ConvertUtil();
                        double[] latlng = convertUtil.xy2lonlat(point.getLng(),point.getLat());
//                        double[] latlng = convertUtil.xy2lonlat(point.getLng(),point.getLat());
                        point.setLng(latlng[0]);
                        point.setLat(latlng[1]);
                    }*/
                    posi.setPoints(points);

                    list.add(posi);
                    restUntil1.setStatus("0");
                    restUntil1.setMsg("请求接口成功");
                }
                restUntil1.setDevices(list);
            } else {
                restUntil1.setStatus("0");
                restUntil1.setMsg("请求接口没有数据");
            }
        } catch (Exception e) {
            restUntil1.setStatus("1");
            restUntil1.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return restUntil1;
    }

    @Override
    public RestUntil1 getTrajectory(Integer cardId, String startTime, String endTime) {
        RestUntil1 restUntil1 = new RestUntil1();
        List<Trajectroy> list = new ArrayList<>();
        try {
            List<Trajectroy> hisT = commonMapper.selectHisTrajectory(cardId, startTime, endTime);
            if (hisT != null && hisT.size() > 0) {
                for (int i = 0; i < hisT.size(); i++) {
                    Trajectroy trajectroy = hisT.get(i);
                    Trajectroy trajectroy1 = new Trajectroy();
                    trajectroy1.setCid(cardId);
                    trajectroy1.setT(trajectroy.getT());
                    String time = trajectroy1.getT();
                    List<Point> points = commonMapper.selectHisTr(cardId, time);
                   /* for (Point point : points) {
                        ConvertUtil convertUtil = new ConvertUtil();
                        double[] latlng = convertUtil.xy2lonlat(point.getLng(),point.getLat());
//                        double[] latlng = convertUtil.xy2lonlat(point.getLng(),point.getLat());
                        point.setLng(latlng[0]);
                        point.setLat(latlng[1]);
                    }*/
                    trajectroy1.setPoints(points);

                    list.add(trajectroy1);
                    restUntil1.setStatus("0");
                    restUntil1.setMsg("请求接口成功");
                }
                restUntil1.setDevices(list);
            } else {
                restUntil1.setStatus("0");
                restUntil1.setMsg("请求接口没有数据");
            }
        } catch (Exception e) {
            restUntil1.setStatus("1");
            restUntil1.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return restUntil1;
    }

    @Override
    public RestUntil1 getBarrierInfo() {
        RestUntil1 restUntil1 = new RestUntil1();
        List<Barriers> list = new ArrayList<>();
        try {
            List<Barriers> barriers = commonMapper.selBarrierInfo();
            if (barriers != null && barriers.size() > 0) {
                for (int i = 0; i < barriers.size(); i++) {
                    Barriers barrier = barriers.get(i);
                    Barriers barriers1 = new Barriers();
                    Integer id = barrier.getBarrierid();
                    barriers1.setBarrierid(id);
                    barriers1.setWorkshopid(barrier.getWorkshopid());
                    List<Point1> points = commonMapper.selectBarrierInfo(id);
//                    for (Point1 point : points) {
//                        ConvertUtil convertUtil = new ConvertUtil();
//                        double[] xy1 = convertUtil.xy2lonlat(point.getLng1(),point.getLat1(),this.selectDm());
//                        double[] xy2 = convertUtil.xy2lonlat(point.getLng2(),point.getLat2(),this.selectDm());
//                        double[] xy3 = convertUtil.xy2lonlat(point.getLng3(),point.getLat3(),this.selectDm());
//                        double[] xy4 = convertUtil.xy2lonlat(point.getLng4(),point.getLat4(),this.selectDm());
//                        point.setLng1(xy1[0]);
//                        point.setLat1(xy1[1]);
//                        point.setLng1(xy2[0]);
//                        point.setLat1(xy2[1]);
//                        point.setLng1(xy3[0]);
//                        point.setLat1(xy3[1]);
//                        point.setLng1(xy4[0]);
//                        point.setLat1(xy4[1]);
//                    }

                    barriers1.setPoints(points);

                    list.add(barriers1);
                    restUntil1.setStatus("0");
                    restUntil1.setMsg("请求接口成功");
                }
                restUntil1.setDevices(list);
            } else {
                restUntil1.setStatus("0");
                restUntil1.setMsg("请求接口没有数据");
            }
        } catch (Exception e) {
            restUntil1.setStatus("1");
            restUntil1.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return restUntil1;
    }

    @Override
    public RestUntil getWarnings() {
        RestUntil restUntil = new RestUntil();
        try {
            List<Warnings> warnings = commonMapper.selWarnings();
            if (CollectionUtils.isEmpty(warnings)) {
                restUntil.setData(warnings);
                restUntil.setStatus("0");
                restUntil.setMsg("请求接口没有数据");
            } else {
                restUntil.setData(warnings);
                restUntil.setStatus("0");
                restUntil.setMsg("请求接口成功");
            }
        } catch (Exception e) {
            restUntil.setStatus("1");
            restUntil.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return restUntil;
    }

    /**
     * 获取信标编号
     *
     * @return
     */

    @Override
    public RestUntil getStaionId() {
        RestUntil restUntil = new RestUntil();
        try {
            List<stationid> stationidList = commonMapper.selStationId();
            if (CollectionUtils.isEmpty(stationidList)) {
                restUntil.setData(stationidList);
                restUntil.setStatus("0");
                restUntil.setMsg("请求接口没有数据");
            } else {
                restUntil.setData(stationidList);
                restUntil.setStatus("0");
                restUntil.setMsg("请求接口成功");
            }
        } catch (Exception e) {
            restUntil.setStatus("1");
            restUntil.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return restUntil;
    }

    @Override
    public Map selectDm() {
        RestUntil restUntil = new RestUntil();
        Map map = new HashMap();
        XTZS sjs = commonMapper.selectDm();
        String[] s = sjs.getSjs().split(",");
        double x11 = Double.parseDouble(s[0]);
        double y11 = Double.parseDouble(s[1]);
        double x22 = Double.parseDouble(s[2]);
        double y22 = Double.parseDouble(s[3]);
        double lng11 = Double.parseDouble(s[4]);
        double lat11 = Double.parseDouble(s[5]);
        double lng22 = Double.parseDouble(s[6]);
        double lat22 = Double.parseDouble(s[7]);
        double angle1 = Double.parseDouble(s[8]);
        double[] xy1 = new double[]{x11, y11};
        double[] xy2 = new double[]{x22, y22};
        double[] lonlat1 = new double[]{lng11, lat11};
        double[] lonlat2 = new double[]{lng22, lat22};
        double angle = angle1;
        map.put("xy1", xy1);
        map.put("xy2", xy2);
        map.put("lonlat1", lonlat1);
        map.put("lonlat2", lonlat2);
        map.put("angle", angle);
        return map;
    }

    /**
     * 和利时实时位置接口
     */
    /*@Override
    public RestUntil selRegionPersonPoint() {
        RestUntil restUntil = new RestUntil();
        try {
            List<PersonPoint> points = commonMapper.selectAllRegions();
            if (CollectionUtils.isEmpty(points)) {
                restUntil.setData(points);
                restUntil.setStatus("200");
                restUntil.setMsg("请求接口没有数据");
            } else {
                for (PersonPoint point : points) {
                    String uuid = UUID.randomUUID().toString();
                    point.setId(uuid);
                    ConvertUtil convertUtil = new ConvertUtil();
                    double[] lanxy = convertUtil.xy2lonlat(Double.parseDouble(point.getLongitude()),
                            Double.parseDouble(point.getLatitude()));
                    point.setLongitude(String.valueOf(lanxy[0]));
                    point.setLatitude(String.valueOf(lanxy[1]));
                    point.setStatus(0);
                }
                restUntil.setData(points);
                restUntil.setStatus("200");
                restUntil.setMsg("请求接口成功");

            }
        } catch (Exception e) {
            restUntil.setStatus("500");
            restUntil.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return restUntil;

    }*/

   @Override
          public RestUntil selRegionPersonPoint() {
              /*RestUntil restUntil = new RestUntil();
               List list = new ArrayList<>();
                List list1 = new ArrayList<>();
              List<PersonPoint> points = new LinkedList<>();
              try {
                  List<PersonPointInfo> regions = commonMapper.selectAllRegion();
                  PersonPointInfo region0 = regions.get(0);
                  int lid = region0.getLayer_id();
                  String lmc = region0.getDm();
                  String lnum = region0.getNumber();

                  if (regions != null && regions.size() > 0) {
                      int sum = 0;
                      for (int i = 0; i <regions.size(); i++) {
                          Region region21 = new Region();
                          PersonPointInfo region = regions.get(i);

                            PersonPointInfo region1 = regions.get(i+1);
                          if (lid != region.getLayer_id()) {
                              region21.setId(lid);
                              region21.setNumber(lnum);
                              region21.setName(lmc);
                              region21.setSum(sum);
                              region21.setPersonPoints(list1);
                              list.add(region21);
                              list.addAll(list1);
                              list1 = new ArrayList<>();

                              list1.clear();
                              lid = region.getLayer_id();
                              lmc = region.getDm();
                              lnum = region.getNumber();
                              sum = 0;
                          }
                           {
                               sum += 1;
//                              region2.setId(region.getLayer_id());
//                              region2.setNumber(region.getNumber1());
//                              region2.setName(region.getDm());
//                              region2.setSum(sum);
                              PersonPointInfo pointInfo = new PersonPointInfo();
                              Map map = new HashMap();
                              pointInfo.setId(region.getId());
                              map.put("id", pointInfo.getId());
                              pointInfo.setCard(region.getCard());
                              map.put("card", pointInfo.getCard());
                             *//* pointInfo.setCard_no(region.getCard_no());
                              map.put("card_no",pointInfo.getCard_no());*//*
                              pointInfo.setName(region.getName());
                              map.put("name", pointInfo.getName());
                              pointInfo.setSex(region.getSex());
                              map.put("sex", pointInfo.getSex());
//                              pointInfo.setStatus(0);
//                              map.put("status",pointInfo.getStatus());
                              pointInfo.setPosition(region.getPosition());
                              map.put("position", pointInfo.getPosition());
                              pointInfo.setWorkType(region.getWorkType());
                              map.put("workType", pointInfo.getWorkType());
                              pointInfo.setX(region.getX());
                              pointInfo.setY(region.getY());
                              ConvertUtil convertUtil = new ConvertUtil();
//                              double[] lanxy = convertUtil.xy2lonlat(Double.parseDouble(pointInfo.getX()),Double.parseDouble(pointInfo.getY()),this.selectDm());
                                double[] lanxy = convertUtil.xy2lonlat(Double.parseDouble(pointInfo.getX()),Double.parseDouble(pointInfo.getY()));
                              pointInfo.setX(String.valueOf(lanxy[0]));
                              pointInfo.setY(String.valueOf(lanxy[1]));

                              map.put("x", pointInfo.getX());
                              map.put("y", pointInfo.getY());
                               *//*pointInfo.setLongitude(region.getLongitude());
                               pointInfo.setLatitude(region.getLatitude());
                               ConvertUtil convertUtil = new ConvertUtil();
                               double[] lanxy = convertUtil.xy2lonlat(Double.parseDouble(pointInfo.getLongitude()),Double.parseDouble(pointInfo.getLatitude()),this.selectDm());
//                                double[] lanxy = convertUtil.xy2lonlat(Double.parseDouble(pointInfo.getX()),Double.parseDouble(pointInfo.getY()));
                               pointInfo.setLongitude(String.valueOf(lanxy[0]));
                               pointInfo.setLatitude(String.valueOf(lanxy[1]));*//*

                               *//*map.put("x", pointInfo.getLongitude());
                               map.put("y", pointInfo.getLatitude());*//*
                             pointInfo.setTime(region.getTime());
                              map.put("time", pointInfo.getTime());
                              pointInfo.setFloor(region.getFloor());
                               map.put("floor",pointInfo.getFloor());
                              pointInfo.setStationId(region.getStationId());
                              map.put("stationId",pointInfo.getStationId());//*
//                               pointInfo.setTime_stamp(region.getTime_stamp());
//                               map.put("time_stamp",pointInfo.getTime_stamp());
//                               pointInfo.setFloor_no(region.getFloor_no());
//                               map.put("floor_no",pointInfo.getFloor_no());
//                               pointInfo.setCompany_code(region.getCompany_code());
//                               map.put("company_code",pointInfo.getCompany_code());
//                               pointInfo.setSpeed(region.getSpeed());
//                               map.put("speed",pointInfo.getSpeed());
//                               pointInfo.setType(region.getType());
//                               map.put("type",pointInfo.getType());

                              points = new LinkedList<>();
                              // map转成实体类
                              PersonPoint user = JSON.parseObject(JSON.toJSONString(map), PersonPoint.class);
                              points.add(user);

                              Collection<PersonPoint> values = map.values();
                              Iterator<PersonPoint> it2 = values.iterator();
                              points = new LinkedList<>();

                              while (it2.hasNext()) {
                                  points.add(it2.next());
                              }
                               Region2 region22 = new Region2();
//                               region22.setPersonPoints(points);
                               for (PersonPoint point : points) {
                                   list1.add(point);
                               }

                          }
                      }
                      Region region21 = new Region();
                      region21.setId(lid);
                      region21.setNumber(lnum);
                      region21.setName(lmc);
                      region21.setSum(sum);
                      region21.setSum(sum);
                      region21.setPersonPoints(list1);

                      list.add(region21);
                      list.addAll(list1);
                      if(list.size()>0) {

                          restUntil.setData(list);
                          restUntil.setStatus("200");
                          restUntil.setMsg("请求接口成功");
                          list = null;
                      }else {
                          restUntil.setData(list);
                          restUntil.setStatus("200");
                          restUntil.setMsg("请求接口没有数据");
                      }
                  } else {
                      restUntil.setData(regions);
                      restUntil.setStatus("200");
                      restUntil.setMsg("请求接口没有数据");
                  }
        } catch (Exception e) {
                restUntil.setStatus("500");
                restUntil.setMsg(e.getMessage());
                e.printStackTrace();
                }
                return restUntil;*/
       RestUntil restUntil = new RestUntil();
       List list = new ArrayList();
       List list1 = new ArrayList();
       List<PersonPoint> points = new LinkedList();
       try
       {
           List<PersonPointInfo> regions = this.commonMapper.selectAllRegion();
           PersonPointInfo region0 = (PersonPointInfo)regions.get(0);
           int lid = region0.getLayer_id().intValue();
           String lmc = region0.getDm();
           String lnum = region0.getNumber();
           if ((regions != null) && (regions.size() > 0))
           {
               int sum = 0;
               for (int i = 0; i < regions.size(); i++)
               {
                   Region region21 = new Region();
                   PersonPointInfo region = (PersonPointInfo)regions.get(i);
                   if (lid != region.getLayer_id().intValue())
                   {
                       region21.setId(Integer.valueOf(lid));
                       region21.setNumber(lnum);
                       region21.setName(lmc);
                       region21.setSum(Integer.valueOf(sum));
                       region21.setPersonPoints(list1);
                       list.add(region21);

                       list1 = new ArrayList();

                       lid = region.getLayer_id().intValue();
                       lmc = region.getDm();
                       lnum = region.getNumber();
                       sum = 0;
                   }
                   sum++;

                   PersonPointInfo pointInfo = new PersonPointInfo();
                   Map map = new HashMap();
                   pointInfo.setId(region.getId());
                   map.put("id", pointInfo.getId());
                   pointInfo.setCard(region.getCard());
                   map.put("card", pointInfo.getCard());
                   pointInfo.setName(region.getName());
                   map.put("name", pointInfo.getName());
                   pointInfo.setSex(region.getSex());
                   map.put("sex", pointInfo.getSex());
                   pointInfo.setPosition(region.getPosition());
                   map.put("position", pointInfo.getPosition());
                   pointInfo.setWorkType(region.getWorkType());
                   map.put("workType", pointInfo.getWorkType());
                   pointInfo.setX(region.getX());
                   pointInfo.setY(region.getY());
                   /*ConvertUtil convertUtil = new ConvertUtil();
                   double[] lanxy = ConvertUtil.xy2lonlat(Double.parseDouble(pointInfo.getX()), Double.parseDouble(pointInfo.getY()));
                   pointInfo.setX(String.valueOf(lanxy[0]));
                   pointInfo.setY(String.valueOf(lanxy[1]));*/

                   map.put("x", pointInfo.getX());
                   map.put("y", pointInfo.getY());
                   pointInfo.setTime(region.getTime());
                   map.put("time", pointInfo.getTime());
                   pointInfo.setFloor(region.getFloor());
                   map.put("floor", pointInfo.getFloor());
                   pointInfo.setStationId(region.getStationId());
                   map.put("stationId", pointInfo.getStationId());

                   points = new LinkedList();

                   PersonPoint user = (PersonPoint)JSON.parseObject(JSON.toJSONString(map), PersonPoint.class);
                   points.add(user);
                   for (PersonPoint point : points) {
                       list1.add(point);
                   }
               }
               Region region21 = new Region();
               region21.setId(Integer.valueOf(lid));
               region21.setNumber(lnum);
               region21.setName(lmc);
               region21.setSum(Integer.valueOf(sum));

               region21.setPersonPoints(list1);

               list.add(region21);
               if (list.size() > 0)
               {
                   restUntil.setData(list);
                   restUntil.setStatus("200");
                   restUntil.setMsg("请求接口成功");
                   list = null;
               }
               else
               {
                   restUntil.setStatus("200");
                   restUntil.setMsg("请求接口无数据");
               }
           }
           else
           {
               restUntil.setStatus("200");
               restUntil.setMsg("请求接口无数据");
           }
       }
       catch (Exception e)
       {
           restUntil.setStatus("500");
           restUntil.setMsg(e.getMessage());
           e.printStackTrace();
       }
       return restUntil;
                }

    /**
     * 获取电子围栏报警接口
     * @return
     */
    @Override
    public RestUntil selFenceAlarmInfo() {
        RestUntil restUntil = new RestUntil();
        try {
            List<Fence> fences = commonMapper.selFenceAlarmInfo();
            if (CollectionUtils.isEmpty(fences)) {
                restUntil.setData(fences);
                restUntil.setStatus("200");
                restUntil.setMsg("请求接口没有数据");
            } else {
                for (Fence fence : fences) {
                    ConvertUtil convertUtil = new ConvertUtil();
                    /*double x = Double.parseDouble(fence.getLat());
                    double y = Double.parseDouble(fence.getLng());*/
                    /*double x = Double.parseDouble(fence.getLongitude());
                    double y = Double.parseDouble(fence.getLatitude());
                    double[] latlng = convertUtil.xy2lonlat(x,y);
//                    double[] latlng = convertUtil.xy2lonlat(x,y);
                    fence.setLongitude(String.valueOf(latlng[0]));
                    fence.setLatitude(String.valueOf(latlng[1]));
                    Integer alType = Integer.parseInt(fence.getType());*/
                    Integer alType = fence.getBjlx();
                    if (alType != null && !"".equals(alType)) {
                       switch (alType) {
                            case 1:
                                fence.setBjName("进入报警");
                                break;
                            case 3:
                                fence.setBjName("超员报警");
                                break;
                            case 5:
                               fence.setBjName("滞留报警");
                                break;
                            case 7:
                                fence.setBjName("串岗报警");
                                break;
                            case 8:
                                fence.setBjName("静止报警");
                                break;
                            case 10:
                                fence.setBjName("静止报警");
                                break;
                        }
                        /**
                         * 和利时报警接口
                         */
                        /*switch (alType) {
                            case 1:
                                fence.setType("oneKeyAlarm:alarm");
                                break;
                            case 3:
                                fence.setType("overNum");
                                break;
                            case 5:
                                fence.setType("stayAlarm");
                                break;
                            case 7:
                                fence.setType("overBoundaryAlarm");
                                break;
                            case 8:
                                fence.setType("stillAlarm");
                                break;
                            case 11:
                                fence.setType("lackNum");
                                break;
                        }*/
                    }
                }
                restUntil.setData(fences);
                restUntil.setStatus("200");
                restUntil.setMsg("请求接口成功");
            }
        } catch (Exception e) {
            restUntil.setStatus("500");
            restUntil.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return restUntil;
    }

    @Override
    public RestUntil selInOrOutInfo() {
        RestUntil restUntil = new RestUntil();
        try {
            List<InOrOutInfo> IOO = commonMapper.selInOrOutInfo();
            if (CollectionUtils.isEmpty(IOO)) {
                restUntil.setData(IOO);
                restUntil.setStatus("200");
                restUntil.setMsg("请求接口没有数据");
            } else {
                restUntil.setData(IOO);
                restUntil.setStatus("200");
                restUntil.setMsg("请求接口成功");

            }
        } catch (Exception e) {
            restUntil.setStatus("500");
            restUntil.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return restUntil;

    }

    @Override
    public RestUntil selPersonInfoByCard(Integer cardId) {
        RestUntil restUntil = new RestUntil();
        try {
            List<Person> personPoints = commonMapper.selPersonInfoByCard(cardId);
            if (CollectionUtils.isEmpty(personPoints)) {
                restUntil.setData(personPoints);
                restUntil.setStatus("200");
                restUntil.setMsg("请求接口没有数据");
            } else {
                /*for (Person personPoint : personPoints) {
                        ConvertUtil convertUtil = new ConvertUtil();
                        double x = Double.parseDouble(personPoint.getX());
                        double y = Double.parseDouble(personPoint.getY());
                        double[] latlng = convertUtil.xy2lonlat(x,y);
//                    double[] latlng = convertUtil.xy2lonlat(x,y);
                        personPoint.setX(String.valueOf(latlng[0]));
                        personPoint.setY(String.valueOf(latlng[1]));
                }*/
                restUntil.setData(personPoints);
                restUntil.setStatus("200");
                restUntil.setMsg("请求接口成功");
            }
        } catch (Exception e) {
            restUntil.setStatus("500");
            restUntil.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return restUntil;
    }

    @Override
    public RestUntil getBattery() {
        RestUntil restUntil = new RestUntil();
        try {
            List<Battery> battery = commonMapper.getBattery();
            if (CollectionUtils.isEmpty(battery)) {
                restUntil.setData(battery);
                restUntil.setStatus("200");
                restUntil.setMsg("请求接口没有数据");
            } else {
                restUntil.setData(battery);
                restUntil.setStatus("200");
                restUntil.setMsg("请求接口成功");

            }
        } catch (Exception e) {
            restUntil.setStatus("500");
            restUntil.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return restUntil;
    }

    @Override
    public RestUntil getStationPos() {
        RestUntil restUntil = new RestUntil();
        try {
            List<JZ_JBXX> JZ = commonMapper.getStationPos();
            if (CollectionUtils.isEmpty(JZ)) {
                restUntil.setData(JZ);
                restUntil.setStatus("200");
                restUntil.setMsg("请求接口没有数据");
            } else {
                /*for (JZ_JBXX jbxx : JZ) {
                    ConvertUtil convertUtil = new ConvertUtil();
                    double x = jbxx.getLng();
                    double y = jbxx.getLat();
                    double[] latlng = convertUtil.xy2lonlat(x,y);
//                    double[] latlng = convertUtil.xy2lonlat(x,y);
                    jbxx.setLng(latlng[0]);
                    jbxx.setLat(latlng[1]);
                }*/
                restUntil.setData(JZ);
                restUntil.setStatus("200");
                restUntil.setMsg("请求接口成功");

            }
        } catch (Exception e) {
            restUntil.setStatus("500");
            restUntil.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return restUntil;
    }

    @Override
    public RestUntil insertRyk(Integer ryId, Integer cardSn) {
        RestUntil restUntil = new RestUntil();
        try {
            int num = commonMapper.insertRyk(ryId,cardSn);
            if (num > 0) {
                restUntil.setData("人员ID为"+ryId+"绑定的定位卡编号:"+cardSn);
                restUntil.setStatus("200");
                restUntil.setMsg("卡片绑定成功");
            }else{
                restUntil.setData("人员ID为"+ryId+"绑定的定位卡编号:"+cardSn);
                restUntil.setStatus("400");
                restUntil.setMsg("卡片绑定失败，查无此人");
            }
        } catch (Exception e) {
            restUntil.setStatus("500");
            restUntil.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return restUntil;
    }
    @Override
    public RestUntil updateRyk(Integer ryId, Integer cardSn) {
        RestUntil restUntil = new RestUntil();
        try {
            int num = commonMapper.updateRyk(ryId,cardSn);
            if (num > 0) {
                restUntil.setData("人员ID为"+ryId+",要新绑定的定位卡编号:"+cardSn);
                restUntil.setStatus("200");
                restUntil.setMsg("卡片修改成功");
            }else {
                restUntil.setData("人员ID为"+ryId+",要新绑定的定位卡编号:"+cardSn);
                restUntil.setStatus("400");
                restUntil.setMsg("卡片修改失败，查无此人");
            }
        } catch (Exception e) {
            restUntil.setStatus("500");
            restUntil.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return restUntil;
    }

    @Override
    public RestUntil deleteRyk(Integer ryId) {
        RestUntil restUntil = new RestUntil();
        try {
            int num = commonMapper.deleteRyk(ryId);
            if (num > 0) {
                restUntil.setData("要删除的定位卡绑定的人员ID为："+ryId);
                restUntil.setStatus("200");
                restUntil.setMsg("卡片删除成功");
            }else {
                restUntil.setData("要删除的定位卡绑定的人员ID为："+ryId);
                restUntil.setStatus("400");
                restUntil.setMsg("卡片删除失败");
            }
        } catch (Exception e) {
            restUntil.setStatus("500");
            restUntil.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return restUntil;
    }

    @Override
    public RestUntil inserGateWay(GateJzxx gateJzxx) {
        RestUntil restUntil = new RestUntil();
        try {
            int num = commonMapper.inserGateWay(gateJzxx);
            if (num > 0) {
                restUntil.setData("新增的基站ID是："+gateJzxx.getStation_id());
                restUntil.setStatus("200");
                restUntil.setMsg("网关基站添加成功");
            }else {
                restUntil.setData("新增的基站ID是："+gateJzxx.getStation_id());
                restUntil.setStatus("400");
                restUntil.setMsg("基站添加失败");
            }
        } catch (Exception e) {
            restUntil.setStatus("500");
            restUntil.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return restUntil;
    }

    @Override
    public RestUntil updateGateWay(GateJzxx gateJzxx) {
        RestUntil restUntil = new RestUntil();
        try {
//            System.out.println("-----------------------"+id);
            System.out.println("-*******************"+gateJzxx.toString());
            int num = commonMapper.updGateWay(gateJzxx);
            if (num > 0) {
                restUntil.setData("要修改的基站ID是："+gateJzxx.getStation_id());
                restUntil.setStatus("200");
                restUntil.setMsg("基站修改成功");
            }else {
                restUntil.setData("要修改的基站ID是："+gateJzxx.getStation_id());
                restUntil.setStatus("400");
                restUntil.setMsg("基站修改失败");
            }
        } catch (Exception e) {
            restUntil.setStatus("500");
            restUntil.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return restUntil;
    }

    @Override
    public RestUntil delGateWay(Integer id) {
        RestUntil restUntil = new RestUntil();
        try {
            int num = commonMapper.delGateWay(id);
            if (num > 0) {
                restUntil.setData("要删除的基站ID为："+id);
                restUntil.setStatus("200");
                restUntil.setMsg("基站删除成功");
            }else {
                restUntil.setData("要删除的基站ID为："+id);
                restUntil.setStatus("400");
                restUntil.setMsg("基站删除失败");
            }
        } catch (Exception e) {
            restUntil.setStatus("500");
            restUntil.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return restUntil;
    }

    @Override
    public RestUntil selGateWay(Integer type,Integer station_id) {
        RestUntil restUntil = new RestUntil();
        try {
            System.out.println(station_id);
            if (station_id == null || "".equals("station_id")) {
                List<GateJzxx> geteJzxx = commonMapper.selGateWay(type);
                if (CollectionUtils.isEmpty(geteJzxx)) {
                    restUntil.setData(geteJzxx);
                    restUntil.setStatus("200");
                    restUntil.setMsg("请求接口没有数据");
                } else {
                    restUntil.setData(geteJzxx);
                    restUntil.setStatus("200");
                    restUntil.setMsg("请求接口成功");

                }
            } else {
                List<GateJzxx> geteJzxx = commonMapper.selGateWayByStatId(station_id);
                if (CollectionUtils.isEmpty(geteJzxx)) {
                    restUntil.setData(geteJzxx);
                    restUntil.setStatus("200");
                    restUntil.setMsg("请求接口没有数据");
                } else {
                    restUntil.setData(geteJzxx);
                    restUntil.setStatus("200");
                    restUntil.setMsg("请求接口成功");

                }
            }
            } catch(Exception e){
                restUntil.setStatus("500");
                restUntil.setMsg(e.getMessage());
                e.printStackTrace();
            }
            return restUntil;
    }

    @Override
    public RestUntil selRykInfoByCSn(Integer cardSn) {
        RestUntil restUntil = new RestUntil();
        try {
                List<PersonInfo> personInfos = commonMapper.selRykInfoByCSn(cardSn);
                if (CollectionUtils.isEmpty(personInfos)) {
                    restUntil.setData(personInfos);
                    restUntil.setStatus("200");
                    restUntil.setMsg("请求接口没有数据");
                } else {
                    restUntil.setData(personInfos);
                    restUntil.setStatus("200");
                    restUntil.setMsg("请求接口成功");
            }
        } catch (Exception e) {
            restUntil.setStatus("500");
            restUntil.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return restUntil;
    }

    @Override
    public RestUntil inserRyJbxx(RyJbxx ryJbxx) {
        RestUntil restUntil = new RestUntil();
        try {
            int num = commonMapper.inserRyJbxx(ryJbxx);
            if (num > 0) {
                restUntil.setData("新增的人员ID是："+ryJbxx.getId());
                restUntil.setStatus("200");
                restUntil.setMsg("人员新增成功");
            }else {
                restUntil.setData("新增的人员ID是："+ryJbxx.getId());
                restUntil.setStatus("400");
                restUntil.setMsg("人员新增失败");
            }
        } catch (Exception e) {
            restUntil.setStatus("500");
            restUntil.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return restUntil;
    }

    @Override
    public RestUntil updateRy(RyJbxx ryJbxx) {
        RestUntil restUntil = new RestUntil();
        try {
//            System.out.println("-----------------------"+id);
//            System.out.println("-*******************"+gateJzxx.toString());
            int num = commonMapper.updateRy(ryJbxx);
            if (num > 0) {
                restUntil.setData("要修改的人员信息ID是："+ryJbxx.getId());
                restUntil.setStatus("200");
                restUntil.setMsg("人员信息修改成功");
            }else {
                restUntil.setData("要修改的人员信息ID是："+ryJbxx.getId());
                restUntil.setStatus("400");
                restUntil.setMsg("人员信息修改失败");
            }
        } catch (Exception e) {
            restUntil.setStatus("500");
            restUntil.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return restUntil;
    }

    @Override
    public RestUntil delRyJbxx(Integer id) {
        RestUntil restUntil = new RestUntil();
        try {
            int num = commonMapper.delRyJbxx(id);
            if (num > 0) {
                restUntil.setData("要删除的人员ID为："+id);
                restUntil.setStatus("200");
                restUntil.setMsg("人员删除成功");
            }else {
                restUntil.setData("要删除的人员ID为："+id);
                restUntil.setStatus("400");
                restUntil.setMsg("人员删除失败");
            }
        } catch (Exception e) {
            restUntil.setStatus("500");
            restUntil.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return restUntil;
    }
}

