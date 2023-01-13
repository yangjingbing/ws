package com.cp.zf.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cp.zf.input.PersonInput;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * <p>
 * 人员有效时间
 * </p>
 *
 * @author cpgu
 * @since 2021-02-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_ry_date")
@ApiModel(value = "TRyDate对象", description = "人员有效时间")
public class TRyDate implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id")
    private String id;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    public static TRyDate of(PersonInput input) {
        TRyDate ryDate = new TRyDate();
        ryDate.setId(input.getId());
        ryDate.setStartTime(parseStringToDateTime(input.getPassageStartTime(), "yyyy-MM-dd HH:mm:ss"));
        ryDate.setEndTime(parseStringToDateTime(input.getPassageEndTime(), "yyyy-MM-dd HH:mm:ss"));
        return ryDate;
    }

    public static LocalDateTime parseStringToDateTime(String time, String format) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern(format);
        return LocalDateTime.parse(time, df);
    }


}
