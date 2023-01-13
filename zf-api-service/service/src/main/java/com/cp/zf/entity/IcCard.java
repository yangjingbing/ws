package com.cp.zf.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_ic_card")
@ApiModel(value="IcCard对象", description="")
public class IcCard implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField("icId")
    private String icId;

    private Integer cardSn;


}
