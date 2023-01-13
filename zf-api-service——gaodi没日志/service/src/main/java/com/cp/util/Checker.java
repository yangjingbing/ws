package com.cp.util;

import com.cp.framwork.core.ApiResponse;
import org.springframework.http.MediaType;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class Checker {
    private static final String SIGN = "sign";
    private static final String APPID= "app_id";
    private static final int unauthorizedErrorCode = HttpServletResponse.SC_UNAUTHORIZED;

    public static List<String> getSignValues(HttpServletRequest request) throws Exception {
        Enumeration<String> paramNames = request.getParameterNames();

        List<String> signValues = new ArrayList<>();
        while (paramNames.hasMoreElements()) {
            String paramName = paramNames.nextElement();
            if (paramName.equals(SIGN)) {
                continue;
            }

            signValues.add(request.getParameter(paramName));
        }

        String body = getRequestBody(request);
        if (body != null) {
            signValues.add(body);
        }
        return signValues;
    }

    public static void writeErrorMessage(HttpServletResponse response, String code, String message) throws Exception {
        response.setStatus(unauthorizedErrorCode);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(response.getOutputStream()));
        writer.write(new ApiResponse<>(code, message, null).toJson());
        writer.close();
    }

    public static String getRequestBody(HttpServletRequest request) throws IOException {
        if (request.getContentLength() <= 0) {
            return null;
        }

        byte[] contents = new byte[request.getContentLength()];
        ServletInputStream stream = request.getInputStream();

        stream.read(contents, 0, request.getContentLength());

        stream.close();
        return new String(contents, request.getCharacterEncoding());
    }

    public static String getRequestUrl(HttpServletRequest request) {
        StringBuffer sb = new StringBuffer();

        Enumeration<String> names = request.getParameterNames();
        while (names.hasMoreElements()) {
            String name = names.nextElement();
            sb.append(name);
            sb.append("=");
            sb.append(request.getParameter(name));
            sb.append("&");
        }
        return sb.toString();
    }
}
