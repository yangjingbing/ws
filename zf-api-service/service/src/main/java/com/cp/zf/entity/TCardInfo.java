package com.cp.zf.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cp.zf.input.CardInput;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_ry_check")
@ApiModel(value="TCardInfo对象", description="核对人员卡号信息")
public class TCardInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "卡号")
    @TableField("card_number")
    private String card_number;

    @ApiModelProperty(value = "检查时间")
    @TableField("time")
    private String time;

    public static TCardInfo of(CardInput input) {
        TCardInfo tCardInfo = new TCardInfo();
        BeanUtils.copyProperties(input,tCardInfo);
        return tCardInfo;
    }
}
