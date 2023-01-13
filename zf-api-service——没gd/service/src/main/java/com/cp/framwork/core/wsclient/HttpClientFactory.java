package com.cp.framwork.core.wsclient;

import akka.stream.ActorMaterializer;
import com.google.common.collect.Maps;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.api.libs.ws.ahc.AhcWSClientConfig;
import play.libs.ws.StandaloneWSClient;
import scala.Option;

import java.util.Map;
import java.util.concurrent.ThreadFactory;

public class HttpClientFactory {
    private static final Logger logger = LoggerFactory.getLogger(HttpClientFactory.class);
    private static ActorMaterializer materializer = ActorMaterializer.apply(Option.apply(null), Option.apply(null), DefaultHttpActorSystem.defaultSystem());
    private static Map<String, StandaloneWSClient> clientMap = Maps.newHashMap();
    private static ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("http-client-common-%d").build();

    /**
     * by getHupoWsClient(String label, ClientConfig clientConfig) throws RuntimeException {
     * @param label
     * @param isSecurity
     * @return
     * @throws RuntimeException
     */
    public static synchronized StandaloneWSClient getHupoWsClient(String label, boolean isSecurity) throws RuntimeException {
        ClientConfig clientConfig = new ClientConfig(isSecurity, Option.apply(null), Option.apply(null), Option.apply(null));
        return getHupoWsClient(label, clientConfig);
    }

    public static synchronized StandaloneWSClient getHupoWsClient(String label, ClientConfig clientConfig) throws RuntimeException {
        String key = label + clientConfig.toString();
        StandaloneWSClient client = clientMap.get(key);

//        HttpTracingConfig tracingConfig = new HttpTracingConfig(label);
        if (client == null) {
            AhcWSClientConfig wsClientConfig = HttpConfig.getConfig(clientConfig);
            logger.info("Create http client {}, {}", label, wsClientConfig.toString());
            client = HupoWsClient.create(
                    wsClientConfig,
                    null,
                    label,
                    materializer,
                    threadFactory
            );

            clientMap.put(key, client);
        }

        return client;
    }

    /**
     * by getHupoWsClient(String label, ClientConfig clientConfig) throws RuntimeException {
     * @param label
     * @param sslConfig
     * @return
     * @throws RuntimeException
     */
    public static synchronized StandaloneWSClient getHupoWsClient(String label, SslConfig sslConfig) throws RuntimeException {
        ClientConfig clientConfig = null;
        if (sslConfig == null) {
            clientConfig = new ClientConfig(false, Option.apply(null), Option.apply(null), Option.apply(null));
        } else {
            clientConfig = new ClientConfig(true, Option.apply(null), Option.apply(sslConfig.keyStorePath()), Option.apply(sslConfig.keystorePass()));
        }
        return getHupoWsClient(label, clientConfig);
    }
}
