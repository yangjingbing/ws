package com.cp.zf.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 *  设备卡对象
 * </p>
 *
 * @since 2021-01-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_ryk_jbxx")
@ApiModel(value="TRykJbxx对象", description="")
public class TRykJbxx implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer sn;

    private Integer ryid;

    private String zt;

    private LocalDateTime lrsj;

    private String lrr;

    private String lx;


}
