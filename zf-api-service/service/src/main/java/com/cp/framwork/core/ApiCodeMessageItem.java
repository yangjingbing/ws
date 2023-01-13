package com.cp.framwork.core;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description ApiCodeMessageItem .</br>
 * <></>
 * @Author gu
 * @Date 2020/9/5 18:06
 * @Version 1.0.0
 **/
@Getter
@Setter
@ToString
public class ApiCodeMessageItem {
    private static Map<String, ApiCodeMessageItem> existedItemMapper;

    private String code;
    private String message;
    private String remark;

    public static List<ApiCodeMessageItem> dumpList() {
        List<ApiCodeMessageItem> list = new ArrayList<>();

        list.addAll(existedItemMapper.values());

        Collections.sort(list, new Comparator<ApiCodeMessageItem>() {
            @Override
            public int compare(ApiCodeMessageItem o1, ApiCodeMessageItem o2) {
                return o1.getCode().compareTo(o2.getCode());
            }
        });

        return list;
    }

    static {
        synchronized (ApiCodeMessageItem.class) {
            existedItemMapper = new ConcurrentHashMap<>();
        }
    }

    /**
     * 是否做code唯一性检查
     * 在映射渠道的message时, 会存在同一个code对应多个message的情况, 此时, uniqueCheck应该设置为false
     * @param code
     * @param message
     * @param remark
     * @param uniqueCheck
     */
    public ApiCodeMessageItem(String code, String message, String remark, boolean uniqueCheck) {
        this.code = code;
        this.message = message;
        this.remark = remark;

        ApiCodeMessageItem existedMessage = existedItemMapper.get(code);
        if (uniqueCheck) {
            if (existedMessage != null && !existedMessage.getMessage().equals(message)) {
                //确保code和message是唯一对应的
                throw new IllegalArgumentException("Code " + code + " has been assigned for message " + existedMessage);
            }
            existedItemMapper.put(code, this);
        }
    }

    public ApiCodeMessageItem(String code, String message, String remark) {
        this(code, message, remark, true);
    }

    public ApiCodeMessageItem(String code, String message) {
        this(code, message, null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ApiCodeMessageItem that = (ApiCodeMessageItem) o;

        if (code != null ? !code.equals(that.code) : that.code != null) return false;
        return message != null ? message.equals(that.message) : that.message == null;

    }

    @Override
    public int hashCode() {
        int result = code != null ? code.hashCode() : 0;
        result = 31 * result + (message != null ? message.hashCode() : 0);
        return result;
    }
}

