package com.cp.zf.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_dw_jbxx")
@ApiModel(value="DwJbxx对象", description="")
public class DwJbxx implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    private String id;

    private String mc;

    private String dz;

    private String fzr;

    private String lxdh;

    private String lxr;

    private LocalDateTime djrq;

    private String zt;

    private String bz;

    private LocalDateTime lrsj;

    private String lrr;


}
