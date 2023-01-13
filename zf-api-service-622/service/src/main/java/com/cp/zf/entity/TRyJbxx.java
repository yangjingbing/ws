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
 * 联系人对象
 * </p>
 *
 * @since 2021-01-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_ry_sbxx")
@ApiModel(value = "TRyJbxx对象", description = "")
public class TRyJbxx implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String uuid;

    private Integer bmid;

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

    @TableField("gz")
    private String gz;

    @ApiModelProperty(value = "房间号")
    private String fjh;

    private String openidyj;

    @TableField("isBlack")
    private Integer isBlack;

    @TableField("start_time")
    private LocalDateTime startTime;

    @TableField("end_time")
    private LocalDateTime endTime;

    public static TRyJbxx of(PersonInput input) {
        TRyJbxx jbxx = new TRyJbxx();
        jbxx.setUuid(input.getId());
        jbxx.setMc(input.getName());
        jbxx.setZjh(input.getIdentity());
        if(input.getSex() == "男" || "男".equals(input.getSex())){
            jbxx.setXb("1");
        } else {
            jbxx.setXb("0");
        }
//        jbxx.setXb(input.getSex());
        jbxx.setAge(input.getAge());//新增的
        jbxx.setSjh1(input.getMobile());
        jbxx.setEntId(input.getEntId());//新增
        if(input.getDeptId() == null || " ".equals(input.getDeptId())){
            jbxx.setBmid(Integer.parseInt("1"));
        }else {
            jbxx.setBmid(Integer.parseInt(input.getDeptId()));
        }
        jbxx.setGz(input.getPersonType() + "");
        jbxx.setZz(input.getAddress());
        jbxx.setZp(input.getHeadPicture());
        jbxx.setIsBlack(input.getIsBlack());
        jbxx.setGh("");//工号
        jbxx.setZt("0");
        jbxx.setStartTime(parseStringToDateTime(input.getPassageStartTime(), "yyyy-MM-dd HH:mm:ss"));
        jbxx.setEndTime(parseStringToDateTime(input.getPassageEndTime(), "yyyy-MM-dd HH:mm:ss"));
        return jbxx;

    }
    public static LocalDateTime parseStringToDateTime(String time, String format) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern(format);
        return LocalDateTime.parse(time, df);
    }

}
