package com.cp.framwork.core.wsclient

import java.util.concurrent.ThreadFactory

import com.google.common.util.concurrent.ThreadFactoryBuilder
import com.typesafe.sslconfig.ssl.SystemConfiguration
import com.typesafe.sslconfig.ssl.debug.DebugConfiguration
import org.slf4j.LoggerFactory
import play.api.libs.ws.ahc.{AhcConfigBuilder, AhcLoggerFactory, AhcWSClientConfig}
import play.shaded.ahc.io.netty.util.HashedWheelTimer
import play.shaded.ahc.org.asynchttpclient.{AsyncHttpClient, DefaultAsyncHttpClient}

object AsyncClientFactory   {
  protected val logger = LoggerFactory.getLogger(this.getClass)  // current logback.xml support play only

  val commonThreadFactory = new ThreadFactoryBuilder().setNameFormat("http-client-common-%d").build()
  val hashTimerThreadFactory = new ThreadFactoryBuilder().setNameFormat("hash-wheel-timer-%d").build()
  val timer = new HashedWheelTimer(hashTimerThreadFactory)
  timer.start()

  def getClient(ahcWSClientConfig: AhcWSClientConfig, threadFactory: ThreadFactory, label: String) : AsyncHttpClient = {

    val key = ahcWSClientConfig.toString

    logger.info(s"create async http client for ${label} ${key}")
    // Create the AHC asyncHttpClient
    val loggerFactory = new AhcLoggerFactory(LoggerFactory.getILoggerFactory())

    // Set up debugging configuration
    if (ahcWSClientConfig.wsClientConfig.ssl.debug.enabled) {
      new DebugConfiguration(loggerFactory).configure(ahcWSClientConfig.wsClientConfig.ssl.debug)
    }

    // Configure the AsyncHttpClientConfig.Builder from the application.conf file...
    val builder = new AhcConfigBuilder(ahcWSClientConfig)
    val ahcBuilder = builder.configure().setKeepAlive(true).setNettyTimer(timer)

    if (threadFactory != null) {
      ahcBuilder.setThreadFactory(threadFactory)
    } else {
      ahcBuilder.setThreadFactory(commonThreadFactory)
    }

    // Set up SSL configuration settings that are global..
    new SystemConfiguration(loggerFactory).configure(ahcWSClientConfig.wsClientConfig.ssl)

    val defaultAsyncHttpClient = new DefaultAsyncHttpClient(ahcBuilder.build())

    defaultAsyncHttpClient
  }
}
