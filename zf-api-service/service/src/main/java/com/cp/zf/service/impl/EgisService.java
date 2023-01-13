package com.cp.zf.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cp.util.CheckUtil;
import com.cp.util.RedisUtil;
import com.cp.zf.entity.*;
import com.cp.zf.input.CarInput;
import com.cp.zf.input.CardInput;
import com.cp.zf.input.PersonInput;
import com.cp.zf.output.RyJbxxOutput;
import com.cp.zf.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
public class EgisService {

    @Autowired
    private TRyJbxxService tRyJbxxService;
    @Autowired
    private TRyDateService tRyDateService;
    @Autowired
    private TCarInfoService tCarInfoService;
    @Autowired
    private TCardInfoService tCardInfoService;
    @Autowired
    private IcCardService icCardService;
    @Autowired
    private RykJbxxService rykJbxxService;
    @Autowired
    private RyJbxxService ryJbxxService;
    @Autowired
    private DwJbxxService dwJbxxService;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private TVolageService tVolageService;

    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = {Exception.class, Error.class})
    public void personInfo(List<PersonInput> personList) throws Exception {
        if (CollectionUtils.isEmpty(personList)) {
            log.info("人员集合为空。");
            return;
        }
        log.info("人员：{}", JSON.toJSONString(personList));
        tRyJbxxService.saveBatch(personList.stream().map(TRyJbxx::of).collect(Collectors.toList()));
        tRyDateService.saveBatch(personList.stream().map(TRyDate::of).collect(Collectors.toList()));
    }
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = {Exception.class, Error.class})
    public void carInfo(List<CarInput> carList) throws Exception {
        if (CollectionUtils.isEmpty(carList)) {
            log.info("车辆集合为空。");
            return;
        }
        log.info("车辆：{}", JSON.toJSONString(carList));
        tCarInfoService.saveBatch(carList.stream().map(TCarInfo::of).collect(Collectors.toList()));
    }
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = {Exception.class, Error.class})
    public RyJbxxOutput cardInfo(List<CardInput> cardList) throws Exception {
        if (CollectionUtils.isEmpty(cardList)) {
            log.info("检查卡片为空。");
            return null;
        }
        log.info("检查卡片：{}", JSON.toJSONString(cardList));
        tCardInfoService.saveBatch(cardList.stream().map(TCardInfo::of).collect(Collectors.toList()));
        //add by cpgu 2021/03/31 begin
        RyJbxxOutput output = new RyJbxxOutput();
        QueryWrapper queryWrapper = new QueryWrapper();
        String cardNumberInput = cardList.get(0).getCard_number();
        output.setIs_have("1");//是否有此人 有0无1
        if (CheckUtil.isNotEmpty(cardNumberInput)) {
            queryWrapper.eq("icId", cardNumberInput);
            IcCard icCard = icCardService.getOne(queryWrapper);
            queryWrapper = new QueryWrapper();
            queryWrapper.eq("sn", icCard.getCardSn());
            RykJbxx rykJbxx = rykJbxxService.getOne(queryWrapper);
            // 新增电量
           /* queryWrapper = new QueryWrapper();
            queryWrapper.eq("cardId",icCard.getCardSn());
            Volage volage = tVolageService.getOne(queryWrapper);
            if(volage.getV().equals(" ") || (" ").equals(volage.getV())){
                output.setV(34);
            } else {
                output.setV(volage.getV());
            }*/
           if(redisUtil.hasKey("v-"+rykJbxx.getSn())){
               if(Objects.isNull(redisUtil.get("v-" + rykJbxx.getSn()))){
                   output.setV(34);
               } else {
                   output.setV((Integer) redisUtil.get("v-" + rykJbxx.getSn()));
               }
           } else {
               output.setV(34);
           }
           /*if(Objects.isNull(redisUtil.get("v-" + rykJbxx.getSn()))){
               output.setV(34);
           } else {
               output.setV((Integer) redisUtil.get("v-" + rykJbxx.getSn()));
           }*/
            if (!Objects.isNull(rykJbxx)) {
                queryWrapper = new QueryWrapper();
                queryWrapper.eq("id", rykJbxx.getRyid());
                RyJbxx ryJbxx = ryJbxxService.getOne(queryWrapper);
                BeanUtils.copyProperties(ryJbxx, output);
                /*TRyDate ryDate = tRyDateService.getById(ryJbxx.getUuid());
                output.setStartTime(ryDate.getStartTime());
                output.setEndTime(ryDate.getEndTime());*/
                queryWrapper = new QueryWrapper();
                queryWrapper.eq("uuid", ryJbxx.getUuid());
                queryWrapper.eq("gh",ryJbxx.getGh());
                queryWrapper.orderByDesc("id");
                queryWrapper.last("limit 1");
                TRyJbxx tRyJbxx = tRyJbxxService.getOne(queryWrapper);
                if(!Objects.isNull(tRyJbxx)){
                    output.setStartTime(tRyJbxx.getStartTime());
                    output.setEndTime(tRyJbxx.getEndTime());
                }
                if(CheckUtil.isNotEmpty(output.getZp())){
                    output.setZp("http://172.16.1.191:8082/park/" + output.getZp());
                }
                queryWrapper = new QueryWrapper();
                queryWrapper.eq("id", ryJbxx.getEntId());
                DwJbxx dwJbxx = dwJbxxService.getOne(queryWrapper);
                output.setQymc(Objects.isNull(dwJbxx) ? "" : dwJbxx.getMc());
                output.setIs_have("0");
            }
        }
//        String carnum = Objects.isNull(redisUtil.getCnStr("C-" + cardList.get(0).getCip())) ? "" : redisUtil.getCnStr("C-" + cardList.get(0).getCip()).toString();
//        carnum = carnum.trim().replace(" ","");
//        output.setCarnum(carnum);
//        output.setGd_status(Objects.isNull(redisUtil.getCnStr("M-" + cardList.get(0).getCip())) ? "" : redisUtil.getCnStr("M-" + cardList.get(0).getCip()).toString());

//        output.setCarnum("123");
        String carnum = Objects.isNull(redisUtil.getCnStr("C-" + cardList.get(0).getCip())) ? "" : redisUtil.getCnStr("C-" + cardList.get(0).getCip()).toString().replace(" ","").trim().substring(1,9);

        if(carnum.equals(" ") || " ".equals(carnum)){
            carnum = "无车牌";
        }
        if(carnum.substring(0,1).equals("无") || "无".equals(carnum.substring(0,1))){
            carnum = carnum.substring(0,3);
            output.setCarnum(carnum);
        } else {
            carnum = carnum.substring(0,8);
            output.setCarnum(carnum);
        }
//      output.setCarnum(Objects.isNull(redisUtil.getCnStr("C-" + cardList.get(0).getCip())) ? "" : redisUtil.getCnStr("C-" + cardList.get(0).getCip()).toString().replace(" ","").trim().substring(1,9));
        output.setGd_status(Objects.isNull(redisUtil.getCnStr("M-" + cardList.get(0).getCip())) ? "" : redisUtil.getCnStr("M-" + cardList.get(0).getCip()).toString());
//        output.setGd_status("1");
        return output;

    }
}
