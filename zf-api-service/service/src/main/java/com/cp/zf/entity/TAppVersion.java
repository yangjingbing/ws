package com.cp.zf.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_app_version")
@ApiModel(value="TAppVersion对象", description="")
public class TAppVersion implements Serializable {

    private static final long serialVersionUID = 1L;

    private String appVersion;

    private String path;


}
