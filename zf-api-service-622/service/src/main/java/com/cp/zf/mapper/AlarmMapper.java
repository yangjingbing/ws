package com.cp.zf.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cp.zf.entity.Alarm;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  预警Mapper 接口
 * </p>
 *
 * @author cpgu
 * @since 2021-01-12
 */
@Mapper
public interface AlarmMapper extends BaseMapper<Alarm> {

    @Select({"<script>",
        "SELECT 1 FROM t_alarm WHERE cardID=#{cardNo} LIMIT 1",
    "</script>"})
    Integer byCardNo(String cardNo);
}
