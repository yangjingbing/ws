package com.cp.zf.service.impl;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @Description 淮安化工园区配置文件 .</br>
 * <></>
 * @Date 2021/01/12 10:23
 * @Version 1.0.0
 **/
@Data
@Component
@PropertySource(value = "classpath:profile/target/huaian_industry_park.properties")
public class HuaianParkConfig  {

    @Value("${app.id}")
    private String appId;
    @Value("${app.key}")
    private String appKey;
    @Value("${app.secret}")
    private String appSecret;

    //统一社会信用代码
    @Value("${org.code}")
    private String orgCode;

    //静态业务数据url
    @Value("${static.url}")
    private String staticUrl;

    //动态业务数据url
    @Value("${dynamic.url}")
    private String dynamicUrl;

    //动态数据上报加密密钥及加密向量
    @Value("${dynamic.report.secret}")
    private String dynamicReportSecret;

    @Value("${place.area.id}")
    private String placeAreaId;

}
