package com.cp.util;

import akka.util.ByteString;
import com.cp.framwork.core.ApiResponse;
import com.cp.framwork.core.wsclient.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.math3.util.Pair;
import play.libs.ws.InMemoryBodyWritable;
import play.libs.ws.StandaloneWSClient;
import play.libs.ws.StandaloneWSRequest;
import play.libs.ws.StandaloneWSResponse;
import scala.Option;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletionStage;
import java.util.function.Function;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;
import static java.util.stream.Collectors.joining;

/**
 * @Description ParkHttpClient .</br>
 * <></>
 * @Author gu
 * @Date 2021/01/12 15:03
 * @Version 1.0.0
 **/
@Slf4j
public class ParkHttpClient extends GenericRetryHttpClient<Map<String, String>, String, Object> {
    @Data
    public static class FickClientConfig {
        private String label = "fick";
        private String url;
        private boolean useSsl;
        private Long requestTimeoutMills;
        private String keyStorePath;
        private String keystorePass;
        private String appId;
        private String secret;

        public ClientConfig toClientConfig() {
            return new ClientConfig(useSsl, Option.apply(requestTimeoutMills), Option.apply(keyStorePath), Option.apply(keystorePass));
        }
    }

    private static final int MAX_RETRIES = 4;


    @Override
    protected int getMaxRetryTimes() {
        return MAX_RETRIES;
    }

    @Override
    protected int getSleepTime(int retries) {
        return 100;
    }

    @Override
    protected CompletionStage<StandaloneWSResponse> doRequestWithTrace(HttpMethod method, String path, Map<String, String> urlParam, String bodyParam, int retries) throws Exception {
        CompletionStage<? extends StandaloneWSResponse> response;


        if (HttpMethod.GET.equals(method)) {
            response = this.get(path, urlParam);
        } else {
            response = this.post(path, bodyParam);
        }

        return response.thenApply((Function<StandaloneWSResponse, StandaloneWSResponse>) standaloneWSResponse -> {
            // 异常检测
            if (standaloneWSResponse.getStatus() / 100 != 2) {
                throw new RuntimeException(standaloneWSResponse.getStatus() + ":" + standaloneWSResponse.getStatusText(), null);
            }
            return standaloneWSResponse;
        }).exceptionally(t -> {
            if (t instanceof RuntimeException) {
                throw (RuntimeException) t;
            } else {
                throw new RuntimeException(t);
            }
        });
    }

    @Override
    protected <T extends Object> T parseResponse(StandaloneWSResponse response, Map<String, String> dxCommonUrl, String dxCommonBody, Class<T> cls) {
        String body = response.getBody();

        if (log.isInfoEnabled()) {
            log.debug("body ret: {}", body);
        }

        if (StringUtils.isBlank(body)) {
            return null;
        }

        ApiResponse<T> apiResponse = null;
        try {
            JsonNode rootNode = mapper.readTree(body);

            apiResponse = new ApiResponse<T>();

            JsonNode dataNode = rootNode.get("data");

            if (!StringUtils.isEmpty(dataNode.toString())) {
                T data = mapper.treeToValue(dataNode, cls);
                apiResponse.setData(data);
            } else {
                apiResponse.setData(null);
            }

            apiResponse.setCode(rootNode.get("code").asText());
            apiResponse.setMessage(rootNode.get("message").asText());

        } catch (Exception e) {
            throw new RemoteException(String.valueOf(response.getStatus()), "Invalid response format ", body, e);
        }

        if (!Objects.equals(apiResponse.getCode(), 200)) {
            throw new RemoteException(response.getStatus(), apiResponse.getMessage(), null);
        }

        return apiResponse.getData();

    }

    private JsonNode parseResponse(StandaloneWSResponse response, Map<String, String> urlParam, String bodyParam) {
        return parseResponse(response);
    }

    private JsonNode parseResponse(StandaloneWSResponse response) {
        String body = null;
        try {
            byte[] bytes = response.getBody().getBytes("ISO-8859-1");
            body = new String(bytes,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RemoteException("-1", e.getMessage(), body);
        }


        if (log.isInfoEnabled()) {
            log.info("body ret: {}", body);
        }

        String code;
        String message;

        JsonNode dataNode;
        try {
            JsonNode node = mapper.readTree(body);
            code = node.get("code").asText();
            message = node.get("msg").asText();
            dataNode = node.get("result");

        } catch (IOException e) {
            throw new RemoteException(200, "Invalid response format :" + body, e);
        }

        if (!Objects.equals("200", code) && !Objects.equals("205", code)) {
            throw new RemoteException(code, message, body);
        }

        return dataNode;
    }

    protected <T> T parseResponse(StandaloneWSResponse response, Map<String, String> urlParam, String bodyParam, TypeReference<T> cls) {
        JsonNode dataNode = parseResponse(response, urlParam, bodyParam);
        T result = mapper.convertValue(dataNode, cls);
        return result;
    }


    public <T> CompletionStage<T> postJsonResult(String path, String bodyParam, Map<String, String> param, TypeReference<T> cls) throws Exception {
        return doRequestWithRetries(HttpMethod.POST, path, param, bodyParam, null).thenApply(r ->
                parseResponse(r.getResponse(), param, null, cls)
        );
    }


    private StandaloneWSClient wsClient;
    private ObjectMapper mapper = new ObjectMapper();
    private FickClientConfig config;

    public ParkHttpClient(FickClientConfig config) {
        // 通过该方法对mapper对象进行设置，所有序列化的对象都将按改规则进行系列化
        // Include.Include.ALWAYS 默认
        // Include.NON_DEFAULT 属性为默认值不序列化
        // Include.NON_EMPTY 属性为 空（""） 或者为 NULL 都不序列化，则返回的json是没有这个字段的。这样对移动端会更省流量
        // Include.NON_NULL 属性为NULL 不序列化
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        mapper.disable(FAIL_ON_UNKNOWN_PROPERTIES);//当反序列化时，属性多了，则忽略该属性，不使其请求报废
        String monitorLabel = config.getLabel();

        this.wsClient = HttpClientFactory.getHupoWsClient(monitorLabel, false);
        this.config = config;
    }


    /**
     * json方式post提交
     *
     * @param path
     * @param urlParam
     * @param bodyStr
     * @return JsonNode
     * @throws Exception
     */
    public CompletionStage<JsonNode> doJsonPost(String path, Map<String, String> headerMap, Map<String, String> urlParam, String bodyStr) throws Exception {

        StandaloneWSRequest request = wsClient.url(config.getUrl() + "/" + path);
        request.setRequestTimeout(Duration.ofMillis(25000));

        if (headerMap != null && !headerMap.isEmpty()) {
            headerMap.forEach((k, v) -> {
                request.addHeader(k, v);
            });
        }
        if (urlParam != null && !urlParam.isEmpty()) {
            urlParam.forEach((k, v) -> {
                request.addQueryParameter(k, v);
            });

        }


//        String bodyStr = bodyParam.toJsonString();
        log.info("Post url {}, header {}, body: {} ", request.getUrl(), headerMap, bodyStr);
        ByteString jsonStr = ByteString.fromString(bodyStr);
        InMemoryBodyWritable body = new InMemoryBodyWritable(jsonStr, "application/json");
//        return request.post(body).thenApply(response -> parseResponse(response)).exceptionally(e -> handleException(e));
        return request.post(body).thenApply(response -> parseResponse(response));

    }

    public <T> CompletionStage<T> doJsonPost(String path, Map<String, String> headerMap, Map<String, String> urlParam, String bodyStr, TypeReference<T> cls) throws Exception {
        return doJsonPost(path, headerMap, urlParam, bodyStr).thenApply(dataNode -> mapper.convertValue(dataNode, cls));
    }

    /**
     * form-data方式post提交
     *
     * @param path
     * @param formData
     * @param retries
     * @return
     * @throws Exception
     */
    public CompletionStage<? extends StandaloneWSResponse> doFormPost(String path, Map<String, String> formData, int retries) throws Exception {

        StandaloneWSRequest request = wsClient.url(config.getUrl() + "/" + path);

        log.info("Post url {}, param {}, body: {} ", request.getUrl(), request.getQueryParameters().toString(), formData);


        List<String> values = new ArrayList<>();
        for (Map.Entry<String, String> item : formData.entrySet()) {
            String key = URLEncoder.encode(item.getKey(), "UTF-8");
            String value = URLEncoder.encode(item.getValue(), "UTF-8");
            values.add(key + "=" + value);
        }
        String s = values.stream().collect(joining("&"));
        ByteString byteString = ByteString.fromString(s);
        InMemoryBodyWritable body = new InMemoryBodyWritable(byteString, "application/x-www-form-urlencoded");

        return request.post(body);
    }

    public CompletionStage<JsonNode> doGet(String path, Map<String, Object> param) throws Exception {

        StandaloneWSRequest request = wsClient.url(config.getUrl() + "/" + path);
        for (Pair<String, String> entry : new UrlPairGenerator().generate(param)) {
            request.addQueryParameter(entry.getKey(), entry.getValue());
        }

        if (log.isDebugEnabled()) {
            log.debug("Get url {}, param: {} ", request.getUrl(), request.getQueryParameters().toString());
        }

        return request.get().thenApply(res -> parseResponse(res));
    }


    private <T> JsonNode handleException(Throwable ex) {
        RemoteException remoteException = ExceptionUtils.searchCause(ex, RemoteException.class);
        if (remoteException != null) {
            if (remoteException.getHttpCode() != 200) {
                throw remoteException;
            }
            ApiResponse apiResponse = new ApiResponse<>(remoteException.getErrorCode(), remoteException.getErrorMessage(), null);

            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.convertValue(apiResponse, JsonNode.class);
        }
        if (ex instanceof RuntimeException) {
            throw (RuntimeException) ex;
        } else {
            throw new RuntimeException(ex);
        }
    }


}
