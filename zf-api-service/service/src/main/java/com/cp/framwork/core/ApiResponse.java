package com.cp.framwork.core;

import com.cp.framwork.constants.ApiCodeConstants;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Setter
@Getter
@ToString
public class ApiResponse<T> {
    private T data;
    private String code = ApiCodeConstants.DEFAULT_SUCCESS.getCode();
    private String message = ApiCodeConstants.DEFAULT_SUCCESS.getMessage();

    public ApiResponse() {
    }

    public ApiResponse(QueryResult<T> result) {
        this.data = result.result();
    }


    public ApiResponse(T data) {
        this.data = data;
    }

/*    public ApiResponse(QueryResult<T> result) {
        this.data = result.result();
    }*/

    public ApiResponse(String code, T data) {
        this.code = code;
        this.data = data;
    }

    public ApiResponse(String code, String message, T data) {
        this(code, data);
        this.message = message;
    }

    public String toJson() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(this.toString(), e);
        }
    }
}
