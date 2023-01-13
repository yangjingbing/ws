package com.ws.task;

import ch.qos.logback.classic.spi.EventArgUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ws.entity.*;
import com.ws.mapper.CommonMapper;
import com.ws.mapper.StaffMapper;
//import com.ws.until.ConvertUtil;
import com.ws.until.RestUntil;
import com.ws.until.SignUtil;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

/**
 * @author isHello
 */
@Component
public class SchedulerTask {

    @Resource
    private StaffMapper staffMapper;
    @Resource
    private CommonMapper commonMapper;


    @Scheduled(cron = "*/3 * * * * ?")
    private void process() throws Exception {
        List sendList = new ArrayList<>();

        StaffPersonAlarm spa = new StaffPersonAlarm();
        List<StaffPersonAlarm> pushDataList = staffMapper.pushAlarmData();
        if (pushDataList != null && pushDataList.size() > 0) {
            for (StaffPersonAlarm alarm : pushDataList) {
                    Integer id = alarm.getId();
                    SendAlarmZT sendZt = staffMapper.getZtById(id);
                    if(0 == sendZt.getSendZT()) {
                        Integer alarmType = alarm.getAlarmType();
                        String personName = alarm.getPersonName();
                        Integer card = alarm.getCard();
                        String layerName = alarm.getLayerName();
                        String cljg = alarm.getCljg();
//                        ConvertUtil convertUtil = new ConvertUtil();
//                        double[] lanxy = ConvertUtil.xy2lonlat(Double.parseDouble(alarm.getX()), Double.parseDouble(alarm.getY()), this.selectDm());
//                        alarm.setX(String.valueOf(lanxy[0]));
//                        alarm.setY(String.valueOf(lanxy[1]));
                        switch (alarmType) {
                            //呼救
                            case 1:
                                alarm.setAlarmLeavel(1);
                                alarm.setAlarmContent(personName + "(" + card + ")呼救报警");
                                break;
                            //低电量
                            case 4:
                                alarm.setAlarmLeavel(2);
                                alarm.setAlarmContent(personName + "(" + card + ")低电量报警");
                                break;
                            case 60:
                                alarm.setAlarmLeavel(3);
                                alarm.setAlarmContent(personName + "(" + card + ")超速报警");
                                break;
                            default:
                        }
                        spa.setLayerName(layerName);
                        spa.setCard(card);
                        spa.setPersonName(personName);
                        spa.setAlarmType(alarmType);
                        spa.setAlarmLeavel(alarm.getAlarmLeavel());
                        spa.setAlarmContent(alarm.getAlarmContent());
                        spa.setBuildingId(alarm.getBuildingId());
                        spa.setBuildingName(alarm.getBuildingName());
                        spa.setId(alarm.getId());
                        spa.setTime(alarm.getTime());
                        spa.setX(alarm.getX());
                        spa.setY(alarm.getY());
                        spa.setLayerId(alarm.getLayerId());
                        spa.setCljg(cljg);
                        sendList.add(spa);
                        staffMapper.updateStaffAlarmSendZt(id);
                    }
                }
//            for (Staff staff : pushDataList) {
//                Integer id = staff.getId();
//                //判断是否新增
//                /*if (staff.getMark().equals("1") && staff.getZt() == 0) {
//                    SendZT sendZT = staffMapper.getSendZT(id);
//                    if (1 == sendZT.getSendZT()) {
//                        Staff staff1 = new Staff();
//                        staff1.setId(id);
//                        staff1.setMc(staff.getMc());
//                        staff1.setGh(staff.getGh());
//                        staff1.setZp(staff.getZp());
//                        staff1.setZpsmall(staff.getZpsmall());
//                        staff1.setZpbig(staff.getZpbig());
//                        staff1.setXx(staff.getXx());
//                        staff1.setCsrq(staff.getCsrq());
//                        staff1.setZwid(staff.getZwid());
//                        staff1.setZwmc(staff.getZwmc());
//                        staff1.setZz(staff.getZz());
//                        staff1.setHkszd(staff.getHkszd());
//                        staff1.setGddh(staff.getGddh());
//                        staff1.setSjh1(staff.getSjh1());
//                        staff1.setDzyx(staff.getDzyx());
//                        staff1.setZjlx(staff.getZjlx());
//                        staff1.setZjh(staff.getZjh());
//                        staff1.setCard_sn(staff.getCard_sn());
//                        staff1.setZt(staff.getZt());
//                        staff1.setDwid(staff.getDwid());
//                        String sex = staff.getSex();
//                        if ("1".equals(sex)) {
//                            staff.setSex("男");
//                        } else if ("2".equals(sex)) {
//                            staff.setSex("女");
//                        } else {
//                            staff.setSex("男");
//                        }
//                        staff1.setSex(staff.getSex());
//                        staff1.setLrsj(staff.getLrsj());
//                        staff1.setLrr(staff.getLrr());
//                        staff1.setGz_show(staff.getGz_show());
//                        staff1.setColor(staff.getGz_show());
//                        staff1.setGz(staff.getGz());
//                        staff1.setGzmc(staff.getGzmc());
//                        staff1.setFjh(staff.getFjh());
//                        staff1.setMark("1");
//                        sendList.add(staff1);
//                        staffMapper.updateStaffSendZT(id);
//                    }
//                }*/
//
//                //判断是否绑定
//                List<StaffNew> staff11 = staffMapper.getStaff1(staff.getMc());
//                if (!CollectionUtils.isEmpty(staff11)){
//                    SendZT sendZT = staffMapper.getSendZTBD(id);
//                    if (1 == sendZT.getSendztbd() && staff.getCard_sn() != null) {
//                        Staff staff1 = new Staff();
//                        staff1.setId(id);
//                        staff1.setMc(staff.getMc());
//                        staff1.setGh(staff.getGh());
//                        staff1.setZp(staff.getZp());
//                        staff1.setZpsmall(staff.getZpsmall());
//                        staff1.setZpbig(staff.getZpbig());
//                        staff1.setXx(staff.getXx());
////                      staff1.setCsrq(staff.getCsrq());
////                      staff1.setZwid(staff.getZwid());
//                        staff1.setZwmc(staff.getZwmc());
//                        staff1.setZz(staff.getZz());
//                        staff1.setHkszd(staff.getHkszd());
//                        staff1.setGddh(staff.getGddh());
//                        staff1.setSjh1(staff.getSjh1());
//                        staff1.setDzyx(staff.getDzyx());
//                        staff1.setZjlx(staff.getZjlx());
//                        staff1.setDwid(staff.getDwid());
//                        staff1.setZjh(staff.getZjh());
//                        staff1.setCard_sn(staff.getCard_sn());
//                        staff1.setZt(staff.getZt());
//                        String sex = staff.getSex();
//                        if ("1".equals(sex)) {
//                            staff.setSex("男");
//                        } else if ("2".equals(sex)) {
//                            staff.setSex("女");
//                        } else {
//                            staff.setSex("男");
//                        }
//                        staff1.setSex(staff.getSex());
//                        staff1.setLrsj(staff.getLrsj());
//                        staff1.setLrr(staff.getLrr());
//                        staff1.setGz_show(staff.getGz_show());
//                        staff1.setColor(staff.getColor());
//                        staff1.setGz(staff.getGz());
//                        staff1.setGzmc(staff.getGzmc());
//                        staff1.setFjh(staff.getFjh());
//                        staff1.setMark(staff.getMark());
//                        sendList.add(staff1);
//                        staffMapper.updateStaffSendZTBD(id);
//                    }
//                }
//
//                //判断是否解绑
//                if (staff.getZt() == 1) {
//                    SendZT sendZT = staffMapper.getSendZTJB(id);
//                    if (1 == sendZT.getSendztjb()) {
//                        Staff staff1 = new Staff();
//                        staff1.setId(id);
//                        staff1.setMc(staff.getMc());
//                        staff1.setGh(staff.getGh());
//                        staff1.setZp(staff.getZp());
//                        staff1.setZpsmall(staff.getZpsmall());
//                        staff1.setZpbig(staff.getZpbig());
//                        staff1.setXx(staff.getXx());
////                        staff1.setCsrq(staff.getCsrq());
////                        staff1.setZwid(staff.getZwid());
//                        staff1.setZwmc(staff.getZwmc());
//                        staff1.setZz(staff.getZz());
//                        staff1.setHkszd(staff.getHkszd());
//                        staff1.setGddh(staff.getGddh());
//                        staff1.setDwid(staff.getDwid());
//                        staff1.setSjh1(staff.getSjh1());
//                        staff1.setDzyx(staff.getDzyx());
//                        staff1.setZjlx(staff.getZjlx());
//                        staff1.setZjh(staff.getZjh());
//                        staff1.setCard_sn(staff.getCard_sn());
//                        staff1.setZt(staff.getZt());
//                        String sex = staff.getSex();
//                        if ("1".equals(sex)) {
//                            staff.setSex("男");
//                        } else if ("2".equals(sex)) {
//                            staff.setSex("女");
//                        } else {
//                            staff.setSex("男");
//                        }
//                        staff1.setSex(staff.getSex());
//                        staff1.setLrsj(staff.getLrsj());
//                        staff1.setLrr(staff.getLrr());
//                        staff1.setGz_show(staff.getGz_show());
//                        staff1.setColor(staff.getColor());
//                        staff1.setGz(staff.getGz());
//                        staff1.setGzmc(staff.getGzmc());
//                        staff1.setFjh(staff.getFjh());
//                        staff1.setMark(staff.getMark());
//                        sendList.add(staff1);
//                        staffMapper.updateStaffSendZTJB(id);
//                    }
//                }
//            }
//                        List list = new ArrayList();
//                        list.add(sendList);
//                        Map map = new HashMap(16);
//                            map.put("data",sendList);
//            System.out.println(map);
//            send("http://172.16.0.201:8082/park/api/getPersonList", map);
//            for(int i = 0; i<sendList.size();i++){
//                  System.out.println(list);
//                  System.out.println(list);
                send("http://39.107.225.125:6012/person-alarm/insert", sendList);
//                   send("http://127.0.0.1", sendList.get(i));
//                   }
        }
    }
    private Map selectDm() {
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

public String send(String url,Object param)throws Exception{
        System.out.println("进入send");
        URL url1=new URL(url);
        URLConnection urlConnection=url1.openConnection();
        // 设置doOutput属性为true表示将使用此urlConnection写入数据
        urlConnection.setDoOutput(true);
        // 定义待写入数据的内容类型，我们设置为application/x-www-form-urlencoded类型
        //urlConnection.setRequestProperty("content-type", "application/x-www-form-urlencoded");
        //urlConnection.setRequestProperty("Accept", "application/json"); // 设置接收数据的格式
        urlConnection.setRequestProperty("Content-Type","application/json;charset=utf-8"); // 设置发送数据的格式
        // 得到请求的输出流对象
//        System.out.println(param);
        OutputStreamWriter out=new OutputStreamWriter(urlConnection.getOutputStream());
        // 把数据写入请求的Body

//        System.out.println("aaaaaaaaaaaaaaaaaaaaaaaa");
        String string=JSON.toJSONString(param);

        System.out.println(string);
//       System.out.println(string);

        out.write(string);

        out.flush();
        out.close();
        // 从服务器读取响应
        InputStream inputStream=urlConnection.getInputStream();
        String encoding=urlConnection.getContentEncoding();
        String body=IOUtils.toString(inputStream,encoding);
        System.out.println(body);

        return body;
        }

        }
