package com.cp.zf.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cp.framwork.core.ApiResponse;
import com.cp.framwork.core.ApiResponseFactory;
import com.cp.util.RedisUtil;
import com.cp.zf.entity.TCar;
import com.cp.zf.input.CarInput;
import com.cp.zf.input.CardInput;
import com.cp.zf.input.GdInfo;
import com.cp.zf.input.PersonInput;
import com.cp.zf.service.ScjNoService;
import com.cp.zf.service.TAppVersionService;
import com.cp.zf.service.TCarService;
import com.cp.zf.service.impl.EgisService;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * @Description 审核通过controller .</br>
 * <></>
 * @Date 2021/2/1 19:10
 * @Version 1.0.0
 **/
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
        egisService.personInfo(personList);
        return ApiResponseFactory.get("ok");
    }

    @ApiOperation("车辆审核通过")
    @PostMapping("/car")
    public ApiResponse carEgis(@RequestBody List<CarInput> carList) throws Exception {
        egisService.carInfo(carList);
        return ApiResponseFactory.get("ok");
    }

    @ApiOperation("人员检查")
    @PostMapping("/rycheck")
    public ApiResponse cardEgis(@RequestBody List<CardInput> cardList) throws Exception {
        System.out.println(ApiResponseFactory.get(egisService.cardInfo(cardList)).toString());
        return ApiResponseFactory.get(egisService.cardInfo(cardList));

    }


    @ApiOperation("获取手持机信息")
    @GetMapping("/scj/{scj}")
    public ApiResponse gainScjInfo(@PathVariable(name = "scj") String scj) throws Exception {
        System.out.println(ApiResponseFactory.get(scjNoService.byScj(scj)).toString());
        return ApiResponseFactory.get(scjNoService.byScj(scj));

    }

    @ApiOperation("上传通行记录")
    @PostMapping("/add-car")
    public ApiResponse addCar(@RequestBody TCar car) throws Exception {
        car.setDt(LocalDateTime.now());
        car.setSendzt(1);//未发送
        System.out.println(car.toString());
        tCarService.save(car);

        //如果接受到的pasted = 1，那就修改redis中g-ip变量的值为1代表抬杠
        //如果接受到的pasted = 2，那么修改redis中g-ip变量的值为0代表不抬杠
        int gIp = Objects.equals("1", car.getPasted()) ? 1 : 0;
        redisUtil.set("G-" + car.getSip(), gIp);
        return ApiResponseFactory.get("ok");

    }

    @ApiOperation("获取app版本信息")
    @GetMapping("/app/version")
    public ApiResponse gainAppVesion() throws Exception {
        return ApiResponseFactory.get(tAppVersionService.getOne(new QueryWrapper<>()));

    }

    @ApiOperation("修改gd状态")
    @PostMapping("/update-gd-status")
    public ApiResponse cardEgis(@RequestBody GdInfo gdInfo) throws Exception {
        return ApiResponseFactory.get(redisUtil.set("M-" + gdInfo.getIp(), gdInfo.getGdStatus()));

    }
}
