package com.cp.zf.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author cpgu
 * @since 2021-03-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_scj_no")
@ApiModel(value="ScjNo对象", description="")
public class ScjNo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "序号")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "手机机号")
    private String scj;

    @ApiModelProperty(value = "卡口")
    private String checkpoint;

    @ApiModelProperty(value = "车道")
    private String driveway;

    @ApiModelProperty(value = "车道对应抓拍机ip")
    private String cIp;

    @ApiModelProperty(value = "1进入0出")
    private Integer status;


}
