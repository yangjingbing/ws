package com.cp.framwork.core.wsclient;

import lombok.Data;
import play.libs.ws.*;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletionStage;

@Data
public class HupoStandaloneWSRequest implements StandaloneWSRequest {
    private StandaloneWSRequest delegated;

    public HupoStandaloneWSRequest(StandaloneWSRequest client) {
        this.delegated = client;
    }

    @Override
    public StandaloneWSRequest setRequestFilter(WSRequestFilter filter) {
        return new HupoStandaloneWSRequest(delegated.setRequestFilter(filter));
    }

    @Override
    public StandaloneWSRequest addHeader(String name, String value) {
        return new HupoStandaloneWSRequest(delegated.addHeader(name, value));
    }

    @Override
    public StandaloneWSRequest setHeaders(Map<String, List<String>> headers) {
        return new HupoStandaloneWSRequest(delegated.setHeaders(headers));
    }

    @Override
    public StandaloneWSRequest setQueryString(String query) {
        return new HupoStandaloneWSRequest(delegated.setQueryString(query));
    }

    @Override
    public StandaloneWSRequest addQueryParameter(String name, String value) {
        return new HupoStandaloneWSRequest(delegated.addQueryParameter(name, value));
    }

    @Override
    public StandaloneWSRequest setQueryString(Map<String, List<String>> params) {
        return new HupoStandaloneWSRequest(delegated.setQueryString(params));
    }

    @Override
    public StandaloneWSRequest addCookie(WSCookie cookie) {
        return new HupoStandaloneWSRequest(delegated.addCookie(cookie));
    }

    @Override
    public StandaloneWSRequest addCookies(WSCookie... cookies) {
        return new HupoStandaloneWSRequest(delegated.addCookies(cookies));
    }

    @Override
    public StandaloneWSRequest setCookies(List<WSCookie> cookies) {
        return new HupoStandaloneWSRequest(delegated.setCookies(cookies));
    }

    @Override
    public StandaloneWSRequest setAuth(String userInfo) {
        return new HupoStandaloneWSRequest(delegated.setAuth(userInfo));
    }

    @Override
    public StandaloneWSRequest setAuth(String username, String password) {
        return new HupoStandaloneWSRequest(delegated.setAuth(username, password));
    }

    @Override
    public StandaloneWSRequest setAuth(String username, String password, WSAuthScheme scheme) {
        return new HupoStandaloneWSRequest(delegated.setAuth(username, password, scheme));
    }

    @Override
    public StandaloneWSRequest sign(WSSignatureCalculator calculator) {
        return new HupoStandaloneWSRequest(delegated.sign(calculator));
    }

    @Override
    public StandaloneWSRequest setFollowRedirects(boolean followRedirects) {
        return new HupoStandaloneWSRequest(delegated.setFollowRedirects(followRedirects));
    }

    @Override
    public StandaloneWSRequest setVirtualHost(String virtualHost) {
        return new HupoStandaloneWSRequest(delegated.setVirtualHost(virtualHost));
    }

    @Override
    public StandaloneWSRequest setRequestTimeout(Duration timeout) {
        return new HupoStandaloneWSRequest(delegated.setRequestTimeout(timeout));
    }

    @Override
    public StandaloneWSRequest setContentType(String contentType) {
        return new HupoStandaloneWSRequest(delegated.setContentType(contentType));
    }

    @Override
    public String getContentType() {
        return delegated.getContentType();
    }

    @Override
    public StandaloneWSRequest setMethod(String method) {
        return new HupoStandaloneWSRequest(delegated.setMethod(method));
    }

    @Override
    public StandaloneWSRequest setBody(BodyWritable bodyWritable) {
        return new HupoStandaloneWSRequest(delegated.setBody(bodyWritable));
    }

    @Override
    public String getUrl() {
        return delegated.getUrl();
    }

    @Override
    public Map<String, List<String>> getHeaders() {
        return delegated.getHeaders();
    }

    @Override
    public List<String> getHeaderValues(String name) {
        return delegated.getHeaderValues(name);
    }

    @Override
    public Optional<String> getHeader(String name) {
        return delegated.getHeader(name);
    }

    @Override
    public Map<String, List<String>> getQueryParameters() {
        return delegated.getQueryParameters();
    }

    @Override
    public String getUsername() {
        return delegated.getUsername();
    }

    @Override
    public String getPassword() {
        return delegated.getPassword();
    }

    @Override
    public WSAuthScheme getScheme() {
        return delegated.getScheme();
    }

    @Override
    public WSSignatureCalculator getCalculator() {
        return delegated.getCalculator();
    }

    @Override
    public Duration getRequestTimeoutDuration() {
        return delegated.getRequestTimeoutDuration();
    }

    @Override
    public boolean getFollowRedirects() {
        return delegated.getFollowRedirects();
    }

    @Override
    public CompletionStage<? extends StandaloneWSResponse> get() {
        return new HupoCompletionStageWrapper<>(delegated.get());
    }

    @Override
    public CompletionStage<? extends StandaloneWSResponse> patch(BodyWritable body) {
        return new HupoCompletionStageWrapper<>(delegated.patch(body));
    }

    @Override
    public CompletionStage<? extends StandaloneWSResponse> post(BodyWritable body) {
        return new HupoCompletionStageWrapper<>(delegated.post(body));
    }

    @Override
    public CompletionStage<? extends StandaloneWSResponse> put(BodyWritable body) {
        return new HupoCompletionStageWrapper<>(delegated.put(body));
    }

    @Override
    public CompletionStage<? extends StandaloneWSResponse> delete() {
        return new HupoCompletionStageWrapper<>(delegated.delete());
    }

    @Override
    public CompletionStage<? extends StandaloneWSResponse> head() {
        return new HupoCompletionStageWrapper<>(delegated.head());
    }

    @Override
    public CompletionStage<? extends StandaloneWSResponse> options() {
        return new HupoCompletionStageWrapper<>(delegated.options());
    }

    @Override
    public CompletionStage<? extends StandaloneWSResponse> execute(String method) {
        return new HupoCompletionStageWrapper<>(delegated.execute(method));
    }

    @Override
    public CompletionStage<? extends StandaloneWSResponse> execute() {
        return new HupoCompletionStageWrapper<>(delegated.execute());
    }

    @Override
    public CompletionStage<? extends StandaloneWSResponse> stream() {
        return new HupoCompletionStageWrapper<>(delegated.stream());
    }
}
