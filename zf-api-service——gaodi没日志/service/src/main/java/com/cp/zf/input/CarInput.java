package com.cp.zf.input;

import lombok.Data;

/**
 * @Description TODO .</br>
 * <></>
 * @Date 2021/2/1 16:30
 * @Version 1.0.0
 **/
@Data
public class CarInput {
    //编号
    private String id;
    //车牌号
    private String carNum;
    //车辆类型
    //0普通车 1危化品车辆  2危废车辆  3工程车  4客车  5货车
    private Integer carType;
    //车牌颜色
    //0蓝色 1黄色 2白色 3黑色 4绿色 5渐变绿 6黄绿
    private Integer carPlateColor;
    //所属企业（可为空）
    private String entId;
    //拜访部门（可为空）
    private String department;
    //通行开始时间
    private String validStime;
    //通行结束时间
    private String validEtime;
}
