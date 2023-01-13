package com.cp.framwork.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Description ApiListResponse .</br>
 * <></>
 * @Author gu
 * @Date 2020/9/5 18:09
 * @Version 1.0.0
 **/
@Setter
@Getter
@ToString
public class ApiListResponse<T> extends ApiResponse<T> {

    private Long total;

    private Long offset;

    private Long limit;

    public ApiListResponse() {
    }

    public ApiListResponse(String code, String msg) {
        super(code, msg, null);
    }

    public ApiListResponse(T data) {
        super(data);
    }

    public ApiListResponse(long total, int offset, int limit, T data) {
        super(data);
        this.total = new Long(total);
        this.offset = new Long(offset);
        this.limit = new Long(limit);
    }

    @Override
    public String toJson() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
