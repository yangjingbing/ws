package com.cp.framwork.core;

import com.cp.framwork.constants.ApiCodeConstants;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.apache.http.HttpStatus;


@Accessors(chain = true)
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ApiException extends Exception {
    private int httpStatus;
    private String code;
    private String errorType;
    private String message;

    @Deprecated
    public ApiException(int httpStatus, int code, String message, Throwable e) {
        this(httpStatus, String.valueOf(code), message, e);
    }

    public ApiException(int httpStatus, ApiCodeMessageItem errorCode, Throwable e) {
        this(httpStatus, errorCode.getCode(), errorCode.getMessage(), e);
    }

    public ApiException(ApiCodeMessageItem errorCode, Throwable e) {
        this(HttpStatus.SC_OK, errorCode, e);
    }

        public ApiException(int httpStatus, String code, String message, Throwable e) {
        super(message, e);

        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }

    public ApiException(int httpStatus, String message, Throwable e) {
        super(message, e);

        this.httpStatus = httpStatus;
        this.code = ApiCodeConstants.DEFAULT_ERROR.getCode();
        this.message = message;
    }
}
