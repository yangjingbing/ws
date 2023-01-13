package com.cp.framwork.core;

import com.cp.framwork.constants.ApiCodeConstants;


public class ApiResponseFactory {
    private ApiResponseFactory() {
    }

    public static <T> ApiResponse<T> get(T data) {
        return new ApiResponse<>(ApiCodeConstants.DEFAULT_SUCCESS.getCode(), ApiCodeConstants.DEFAULT_SUCCESS.getMessage(), data);
    }

    public static <T> ApiResponse<T> get(ApiCodeMessageItem apiCodeMessageItem) {
        return new ApiResponse(apiCodeMessageItem.getCode(), apiCodeMessageItem.getMessage(), apiCodeMessageItem.getRemark());
    }

    public static <T> ApiListResponse<T> get(QueryResult<T> result) {
        return get(result.totalNumber(), result.offset(), result.limit(), result.result());
    }

    public static <T> ApiListResponse<T> get(long total, long offset, long limit, T data) {
        ApiListResponse<T> response = new ApiListResponse<>(total, (int) offset, (int) limit, data);
        response.setCode(ApiCodeConstants.DEFAULT_ERROR.getCode());
        response.setMessage(ApiCodeConstants.DEFAULT_ERROR.getMessage());

        return response;
    }

    public static <T> ApiResponse<T> get(String code, String msg, T data) {
        return new ApiResponse<>(code, msg, data);
    }


}
