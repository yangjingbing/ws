package com.cp.zf.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_rssi_backup")
@ApiModel(value="ic卡电量对象", description="")
public class Volage implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("cardId")
    private String cardId;

    @TableField("v")
    private Integer v;

    private LocalDateTime dt;
}
