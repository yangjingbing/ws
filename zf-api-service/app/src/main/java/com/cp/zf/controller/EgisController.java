package com.cp.zf.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cp.framwork.core.ApiResponse;
import com.cp.framwork.core.ApiResponseFactory;
import com.cp.util.RedisUtil;
import com.cp.zf.entity.TAppVersion;
import com.cp.zf.entity.TCar;
import com.cp.zf.input.CarInput;
import com.cp.zf.input.CardInput;
import com.cp.zf.input.GdInfo;
import com.cp.zf.input.PersonInput;
import com.cp.zf.output.RyJbxxOutput;
import com.cp.zf.output.ScjNoOutput;
import com.cp.zf.service.ScjNoService;
import com.cp.zf.service.TAppVersionService;
import com.cp.zf.service.TCarService;
import com.cp.zf.service.impl.EgisService;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import com.cp.util.CompareDate;

@Slf4j
@RestController
@RequestMapping("/{version}/zf/egis")
public class EgisController {

    @Autowired
    private EgisService egisService;
    @Autowired
    private ScjNoService scjNoService;
    @Autowired
    private TCarService tCarService;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private TAppVersionService tAppVersionService;


    @ApiOperation("人员审核通过")
    @PostMapping("/user")
    public ApiResponse userEgis(@RequestBody List<PersonInput> personList) throws Exception {
        log.info("人员审核通过=>input param:{}", JSON.toJSONString(personList));
        egisService.personInfo(personList);
        return ApiResponseFactory.get("ok");
    }

    @ApiOperation("车辆审核通过")
    @PostMapping("/car")
    public ApiResponse carEgis(@RequestBody List<CarInput> carList) throws Exception {
        log.info("车辆审核通过=>input param:{}", JSON.toJSONString(carList));

        egisService.carInfo(carList);
        return ApiResponseFactory.get("ok");
    }

    @ApiOperation("人员检查")
    @PostMapping("/rycheck")
    public ApiResponse cardEgis(@RequestBody List<CardInput> cardList) throws Exception {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date1 = df.parse(df.format(new Date()));
        Date date2 = df.parse("2022-08-01 00:00:00");
        if(CompareDate.compareDate(date1,date2)) {
            log.info("人员检查=>input param:{}", JSON.toJSONString(cardList));

            RyJbxxOutput output = egisService.cardInfo(cardList);
            log.info("人员检查=>output dataNode value:{}", JSON.toJSONString(output));

            return ApiResponseFactory.get(output);
        } else {
            System.exit(0);
        }
        return ApiResponseFactory.get("ok");
    }


    @ApiOperation("获取手持机信息")
    @GetMapping("/scj/{scj}")
    public ApiResponse gainScjInfo(@PathVariable(name = "scj") String scj) throws Exception {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date1 = df.parse(df.format(new Date()));
        Date date2 = df.parse("2022-08-01 00:00:00");
        if(CompareDate.compareDate(date1,date2)) {
            log.info("获取手持机信息=>input param:{}", scj);
            ScjNoOutput output = scjNoService.byScj(scj);
            log.info("获取手持机信息=>output dataNode value:{}", output);
            return ApiResponseFactory.get(output);
        }else{
            System.exit(0);
        }
        return ApiResponseFactory.get("ok");
    }

    @ApiOperation("上传通行记录")
    @PostMapping("/add-car")
    public ApiResponse addCar(@RequestBody TCar car) throws Exception {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date1 = df.parse(df.format(new Date()));
        Date date2 = df.parse("2022-08-01 00:00:00");
        if(CompareDate.compareDate(date1,date2)) {
            log.info("上传通行记录=>input param:{}", JSON.toJSONString(car));

            car.setDt(LocalDateTime.now());
            car.setSendzt(1);//未发送
            tCarService.save(car);

            //如果接受到的pasted = 1，那就修改redis中g-ip变量的值为1代表抬杠
            //如果接受到的pasted = 2，那么修改redis中g-ip变量的值为0代表不抬杠
            int gIp = Objects.equals("1", car.getPasted()) ? 1 : 0;
            redisUtil.set("G-" + car.getSip(), gIp);
            return ApiResponseFactory.get("ok");
        } else {
            System.exit(0);
        }
        return ApiResponseFactory.get("ok");
    }

    @ApiOperation("获取app版本信息")
    @GetMapping("/app/version")
    public ApiResponse gainAppVesion() throws Exception {
        TAppVersion output = tAppVersionService.getOne(new QueryWrapper<>());
        log.info("获取app版本信息=>output dataNode :{}", JSON.toJSONString(output));

        return ApiResponseFactory.get(output);
    }

    @ApiOperation("修改gd状态")
    @PostMapping("/update-gd-status")
    public ApiResponse cardEgis(@RequestBody GdInfo gdInfo) throws Exception {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date1 = df.parse(df.format(new Date()));
        Date date2 = df.parse("2022-08-01 00:00:00");
        if(CompareDate.compareDate(date1,date2)) {
            log.info("修改gd状态=>input param:{}", JSON.toJSONString(gdInfo));
            return ApiResponseFactory.get(redisUtil.set("M-" + gdInfo.getIp(), gdInfo.getGdStatus()));
        }else{
            System.exit(0);
        }
        return ApiResponseFactory.get("ok");
    }

}
