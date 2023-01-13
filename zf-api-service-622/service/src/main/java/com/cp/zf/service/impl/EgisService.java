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

/**
 * @Description 审核通过service .</br>
 * <></>
 * @Author gu
 * @Date 2021/2/18 21:25
 * @Version 1.0.0
 **/
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
            if (!Objects.isNull(rykJbxx)) {
                queryWrapper = new QueryWrapper();
                queryWrapper.eq("id", rykJbxx.getRyid());

                RyJbxx ryJbxx = ryJbxxService.getOne(queryWrapper);
                BeanUtils.copyProperties(ryJbxx, output);
                /*TRyDate ryDate = tRyDateService.getById(ryJbxx.getUuid());
                output.setStartTime(ryDate.getStartTime());
                output.setEndTime(ryDate.getEndTime());*/
                queryWrapper = new QueryWrapper();
                queryWrapper.eq("uuid",ryJbxx.getUuid());
                queryWrapper.orderByDesc("id");
                queryWrapper.last("limit 1");
                TRyJbxx tRyJbxx = tRyJbxxService.getOne(queryWrapper);
                if(!Objects.isNull(tRyJbxx)){
                    output.setStartTime(tRyJbxx.getStartTime());
                    output.setEndTime(tRyJbxx.getEndTime());
                }

                if(CheckUtil.isNotEmpty(output.getZp()))
                    //外网地址
//                    output.setZp("http://222.188.116.18:9082/park" + output.getZp());
                    // 内网地址
                    output.setZp("http://172.16.0.201:8082/park/" + output.getZp());

                queryWrapper = new QueryWrapper();
                queryWrapper.eq("id", ryJbxx.getEntId());
                DwJbxx dwJbxx = dwJbxxService.getOne(queryWrapper);

                output.setQymc(Objects.isNull(dwJbxx) ? "" : dwJbxx.getMc());
                output.setIs_have("0");

            }
        }
//        output.setCarnum(Objects.isNull(redisUtil.getCnStr("C-" + cardList.get(0).getCip())) ? "" : redisUtil.getCnStr("C-" + cardList.get(0).getCip()).toString());
//        output.setGd_status(Objects.isNull(redisUtil.getCnStr("M-" + cardList.get(0).getCip())) ? "" : redisUtil.getCnStr("M-" + cardList.get(0).getCip()).toString());
        output.setCarnum(Objects.isNull(redisUtil.getCnStr("C-" + cardList.get(0).getCip())) ? "" : redisUtil.getCnStr("C-" + cardList.get(0).getCip()).toString().replace(" ","").trim().substring(1,9));
        output.setGd_status(Objects.isNull(redisUtil.getCnStr("M-" + cardList.get(0).getCip())) ? "" : redisUtil.getCnStr("M-" + cardList.get(0).getCip()).toString());
//        output.setCarnum(Objects.isNull(redisUtil.getCnStr("C-" + cardList.get(0).getCip())) ? "" : redisUtil.getCnStr("C-" + cardList.get(0).getCip()).toString());
//        output.setGd_status(Objects.isNull(redisUtil.getCnStr("M-" + cardList.get(0).getCip())) ? "" : redisUtil.getCnStr("M-" + cardList.get(0).getCip()).toString());


        return output;

        //end by 2021/03/11
    }
}
