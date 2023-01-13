package com.cp.framwork.core.wsclient;

import akka.stream.Materializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.api.libs.ws.ahc.AhcWSClientConfig;
import play.api.libs.ws.ahc.cache.AhcHttpCache;
import play.api.libs.ws.ahc.cache.CachingAsyncHttpClient;
import play.libs.ws.StandaloneWSClient;
import play.libs.ws.ahc.StandaloneAhcWSClient;
import play.shaded.ahc.org.asynchttpclient.AsyncHttpClient;

import java.util.concurrent.ThreadFactory;


public class HupoWsClient {
    private final static Logger logger = LoggerFactory.getLogger(HupoWsClient.class);

    public static StandaloneWSClient create(AhcWSClientConfig ahcWSClientConfig, AhcHttpCache cache, String metricTag, Materializer materializer) {
        return create(ahcWSClientConfig, cache, metricTag, materializer,  null);
    }

    public static StandaloneWSClient create(AhcWSClientConfig ahcWSClientConfig,
                                            AhcHttpCache cache,
                                            String metricTag,
                                            Materializer materializer,
                                            ThreadFactory threadFactory) {
        AsyncHttpClient ahcClient = null;
        AsyncHttpClient defaultAsyncHttpClient = AsyncClientFactory.getClient(ahcWSClientConfig, threadFactory, metricTag);
        if (cache != null) {
            logger.info("HttpClient use cache");
            ahcClient = new CachingAsyncHttpClient(defaultAsyncHttpClient, cache);
        } else {
            ahcClient = defaultAsyncHttpClient;
        }

       /* if (metricTag != null && !metricTag.isEmpty()) {
            logger.info("HttpClient use monitoring");
            ahcClient = new MonitoringAsyncHttpClient(ahcClient, metricTag);
        }

        // tracing
        if (tracingConfig != null) {
            logger.info("Http client with tracing: {}", tracingConfig);
            HttpTracing httpTracing = HttpTracingFactory.getTracing(tracingConfig);
            ahcClient = new TracingAsyncHttpClient(ahcClient, httpTracing);
        }
*/
        return new HupoStandaloneWsClient(new StandaloneAhcWSClient(ahcClient, materializer));
    }
}

