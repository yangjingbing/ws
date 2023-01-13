package com.cp.zf.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author cpgu
 * @since 2021-03-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_ry_jbxx")
@ApiModel(value="RyJbxx对象", description="")
public class RyJbxx implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "人员ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "部门")
    private Long bmid;

    @ApiModelProperty(value = "姓名")
    private String mc;

    @ApiModelProperty(value = "照片")
    private String zp;

    @ApiModelProperty(value = "照片小")
    private String zpsmall;

    private String zpbig;

    @ApiModelProperty(value = "工号")
    private String gh;

    @ApiModelProperty(value = "性别")
    private String xb;

    @ApiModelProperty(value = "血型")
    private String xx;

    @ApiModelProperty(value = "出生日期")
    private LocalDate csrq;

    @ApiModelProperty(value = "职务")
    private String zwmc;

    @ApiModelProperty(value = "住址")
    private String zz;

    private String hkszd;

    private String gddh;

    @ApiModelProperty(value = "手机号码")
    private String sjh1;

    @ApiModelProperty(value = "邮箱")
    private String dzyx;

    @ApiModelProperty(value = "证件类型")
    private String zjlx;

    @ApiModelProperty(value = "证件号")
    private String zjh;

    @ApiModelProperty(value = "状态")
    private String zt;

    @ApiModelProperty(value = "录入时间")
    private LocalDateTime lrsj;

    @ApiModelProperty(value = "录入人")
    private String lrr;

    @ApiModelProperty(value = "跟踪状态")
    @TableField("GZ_show")
    private String gzShow;

    @ApiModelProperty(value = "颜色")
    private String color;

    @ApiModelProperty(value = "工种")
    @TableField("gz")
    private String gz;

    @ApiModelProperty(value = "房间号")
    private String fjh;

    private String openidyj;

    @ApiModelProperty(value = "标记")
    private Integer mark;

    @ApiModelProperty(value = "接口绑定发送状态")
    private Integer sendzt;

    @ApiModelProperty(value = "接口解绑发送状态")
    private Integer sendztbd;

    @ApiModelProperty(value = "接口挂失发送状态")
    private Integer sendztjb;

    @ApiModelProperty(value = "年龄")
    private Integer age;

    @ApiModelProperty(value = "所属企业ID")
    @TableField("entId")
    private String entId;

    @ApiModelProperty(value = "是否黑名单")
    @TableField("isBlack")
    private Integer isBlack;

    @ApiModelProperty(value = "唯一标识")
    private String uuid;

    @ApiModelProperty(value = "卡号")
    private String cardSn;

    @ApiModelProperty(value = "发卡时间")
    private LocalDateTime cardDate;

    @ApiModelProperty(value = "发卡人")
    private String cardInput;


}
