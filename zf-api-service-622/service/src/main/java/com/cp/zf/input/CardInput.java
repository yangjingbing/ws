package com.cp.zf.input;

import lombok.Data;

/**
 * @date 2021/3/10 15:27
 */
@Data
public class CardInput {
    // 卡号
    private String card_number;

    private String cip;

    // 时间
    private String time;
}
