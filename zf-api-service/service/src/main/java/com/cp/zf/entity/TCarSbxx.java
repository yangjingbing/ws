package com.cp.zf.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cp.zf.input.CarInput;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_car_sbxx")
@ApiModel(value="TCarSbxx对象", description="车辆设备信息")
public class TCarSbxx implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id")
    private String id;

    @ApiModelProperty(value = "车牌号")
    @TableField("carNum")
    private String carNum;

    @ApiModelProperty(value = "车辆类型0普通车 1危化品车辆  2危废车辆  3工程车  4客车  5货")
    @TableField("carType")
    private Integer carType;

    @ApiModelProperty(value = "车牌颜色0蓝色 1黄色 2白色 3黑色 4绿色 5渐变绿 6黄绿")
    @TableField("carPlateColor")
    private Integer carPlateColor;

    @ApiModelProperty(value = "所属企业（可为空）")
    @TableField("entId")
    private String entId;

    @ApiModelProperty(value = "拜访部门（可为空）")
    private String department;

    @ApiModelProperty(value = "通行开始时间")
    @TableField("validStime")
    private String validStime;

    @ApiModelProperty(value = "通行结束时间")
    @TableField("validEtime")
    private String validEtime;

    public static TCarSbxx of(CarInput input){
        TCarSbxx tCarInfo = new TCarSbxx();
        BeanUtils.copyProperties(input,tCarInfo);
        tCarInfo.setId(input.getId());
        return tCarInfo;
    }

}
