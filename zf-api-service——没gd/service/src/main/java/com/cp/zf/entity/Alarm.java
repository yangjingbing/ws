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

/**
 * <p>
 * 预警
 * </p>
 *
 * @author cpgu
 * @since 2021-01-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_alarm")
@ApiModel(value="Alarm对象", description="")
public class Alarm implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("cardID")
    private Integer cardID;

    @TableField("sourceStationID")
    private Integer sourceStationID;

    @TableField("DT")
    private LocalDateTime dt;

    @TableField("messageType")
    private Integer messageType;

    private Integer status;

    private String cljg;

    private Double geoX;

    private Double geoY;

    private String insideQyids;

    private String insideQymcs;

    private Integer layerId;

    private String layerName;

    private String mc;


}
