package com.cp.framwork.core.wsclient;

import lombok.Data;
import play.libs.ws.StandaloneWSResponse;

@Data
public class CommonClientResult<T> {
    private StandaloneWSResponse response;
    private T commonResult;
}
