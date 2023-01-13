package com.cp.zf.input;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class PersonInput {
    private String id;//编号
    private String name;//姓名
    private String identity;//身份证号
    private String sex;//性别
    private Integer age;//年龄
    private String mobile;//  手机号
    private String entId;//所属企业（可为空）
    private String deptId;//拜访部门（可为空）
    //人员类型
    //1.企业职工
    //2.企业第三方
    //3.涉及公用工程第三方
    //4.其他外来人员
    //5、社区居民及周边
    private Integer personType;
    //地址
    private String address;
    //头像
    private String headPicture;
    //有效开始时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String passageStartTime;
    //有效结束时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String passageEndTime;

    //卡号
    private String cardSn;
    private Integer isBlack;
    // 拜访企业
    private String targetEntId;
}
