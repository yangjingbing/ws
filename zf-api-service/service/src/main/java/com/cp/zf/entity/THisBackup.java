package com.cp.zf.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 *   实时位置
 * </p>
 *
 * @author cpgu
 * @since 2021-01-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_his_backup")
@ApiModel(value="THisBackup对象", description="")
public class THisBackup implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Double geoX;

    private Double geoY;

    private Integer cardSn;

    private Integer layerId;

    private LocalDateTime dt;

    private String insideQyids;

    private String insideQymcs;

    private Integer nearlyStation1;

    private Double xx;

    private Double yy;

    private Integer fid;


}
