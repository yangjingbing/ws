package com.cp.zf.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cp.zf.input.PersonInput;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * <p>
 * 人员设备信息
 * </p>
 *
 * @author cpgu
 * @since 2021-01-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_ry_sbxx")
@ApiModel(value = "TRySbxx对象", description = "")
public class TRySbxx implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String uuid;

    private String bmid;

    private String mc;

    private String zp;

    private String zpsmall;

    private String zpbig;

    private String gh;

    private String xb;

    private Integer age;

    private String xx;

    private LocalDate csrq;

    private String zwmc;

    private String zz;

    private String hkszd;

    private String gddh;

    private String sjh1;

    private String dzyx;

    @TableField("entId")
    private String entId;

    private String zjlx;

    private String zjh;

    private String zt;

    private LocalDateTime lrsj;

    private String lrr;

    @TableField("GZ_show")
    private String gzShow;

    private String color;

    private String gz;

    @ApiModelProperty(value = "房间号")
    private String fjh;

    private String openidyj;

    @TableField("isBlack")
    private Integer isBlack;

    @TableField("targetEntId")
    private String targetEntId;


    public static TRySbxx of(PersonInput input) {
        TRySbxx jbxx = new TRySbxx();
        jbxx.setUuid(input.getId());
        jbxx.setMc(input.getName());
        jbxx.setZjh(input.getIdentity());
//        jbxx.setXb(input.getSex());
        if(input.getSex().equals("男") || "男".equals(input.getSex())){
            jbxx.setXb("0");
        }else {
            jbxx.setXb("1");
        }
        jbxx.setAge(input.getAge());//新增的
        jbxx.setSjh1(input.getMobile());
        jbxx.setEntId(input.getEntId());//新增
        jbxx.setBmid(input.getDeptId());
        jbxx.setGz(input.getPersonType() + "");
        jbxx.setZz(input.getAddress());
        jbxx.setZp(input.getHeadPicture());
        jbxx.setIsBlack(input.getIsBlack());
        jbxx.setGh("");//工号
        jbxx.setZt("0");
        jbxx.setTargetEntId(input.getTargetEntId());
        return jbxx;


    }

}
