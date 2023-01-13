package com.cp.zf.constants;

import lombok.Getter;

import java.util.Objects;

public enum StaticModuleTypeEnum {
    DEVICE_CARD_INFO("QY_SBKXX", "设备卡信息");


    @Getter
    private String code;
    @Getter
    private String name;


    StaticModuleTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static StaticModuleTypeEnum getByCode(String code) {
        for (StaticModuleTypeEnum type : StaticModuleTypeEnum.values()) {
            if (Objects.equals(type.getCode(), code)) {
                return type;
            }
        }
        throw new IllegalArgumentException("无法根据传参匹配到类型 ");
    }
}
