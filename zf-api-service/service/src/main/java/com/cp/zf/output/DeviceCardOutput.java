package com.cp.zf.output;

import com.cp.zf.entity.TRykJbxx;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Description 设备卡 .</br>
 * <></>
 * @Author gu
 * @Date 2021/1/12 13:17
 * @Version 1.0.0
 **/
@Data
public class DeviceCardOutput {

    @ApiModelProperty(name = "业务编码")
    private String enterpriseId;

    @ApiModelProperty(name = "企业社会统一信用代码")
    private String serviceCode;

    private List<Device> dataList;

    @Data
    public static class Device {
        @ApiModelProperty(name = "企业编码")
        private String enterpriseId;

        @ApiModelProperty(name = "设备卡主键")
        private String deviceId;

        @ApiModelProperty(name = "设备卡卡号")
        private String deviceNo;

        @ApiModelProperty(name = "设备卡状态0无效,1有效,2报警")
        private String deviceStat;

        @ApiModelProperty(name = "设备卡关联的人员姓名")
        private String personName;

        @ApiModelProperty(name = "设备所属企业区域信息主键")
        private String placeAreaId;


        @ApiModelProperty(name = "对接系统数据主键iddataId")
        private String dataId;

    }

}
