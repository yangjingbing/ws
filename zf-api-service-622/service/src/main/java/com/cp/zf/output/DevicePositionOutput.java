package com.cp.zf.output;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description 设备位置 .</br>
 * <></>
 * @Author gu
 * @Date 2021/1/12 18:24
 * @Version 1.0.0
 **/
@Data
public class DevicePositionOutput {
    @ApiModelProperty(name = "企业编码")
    private String enterpriseId;

    @ApiModelProperty(name = "设备卡卡号")
    private String deviceNo;

    @ApiModelProperty(name = "设备卡状态0无效,1有效,2报警")
    private String deviceStat;

    @ApiModelProperty(name = "经度")
    private Double longitude;

    @ApiModelProperty(name = "纬度")
    private Double latitude;

    @ApiModelProperty(name = "实时数据的时间戳")
    private Long timestampMillisecond;
}
