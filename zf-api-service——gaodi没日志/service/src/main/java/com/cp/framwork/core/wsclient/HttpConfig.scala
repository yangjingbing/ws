package com.cp.framwork.core.wsclient

import com.typesafe.sslconfig.ssl.{KeyManagerConfig, KeyStoreConfig, SSLConfigSettings, SSLLooseConfig}
import play.api.libs.ws.WSClientConfig
import play.api.libs.ws.ahc.AhcWSClientConfig

import scala.concurrent.duration._

/**
  * Created by cpgu on 17/7/19.
  */
object HttpConfig {

  def getConfig(isSecurity: Boolean, timeOutMills: Long): AhcWSClientConfig = {
    val loose = SSLLooseConfig().withAcceptAnyCertificate(true)
    val sSLConfigSettings = SSLConfigSettings().withLoose(loose)
    val defaultClientConfig = WSClientConfig(
      connectionTimeout = timeOutMills.millis,
      requestTimeout = timeOutMills.millis)

    if (isSecurity) {
      AhcWSClientConfig(
        wsClientConfig = defaultClientConfig.copy(ssl = sSLConfigSettings)
      )
    } else {
      AhcWSClientConfig(
        wsClientConfig = defaultClientConfig
      )
    }
  }



  def getConfig(config: ClientConfig): AhcWSClientConfig = {
    val timeOut = if (config.requestTimeoutMills.isDefined) {
      config.requestTimeoutMills.get.millis
    } else {
      10.second
    }

    val defaultWsClientConfig = WSClientConfig(
      connectionTimeout = 20.second,
      idleTimeout = 30.second,
      requestTimeout = timeOut.toMillis.millis
    )

    if (config.useSsl) {
      if (config.keyStorePath.isDefined) {
        val keyStoreConfig = KeyStoreConfig(None, config.keyStorePath).withPassword(config.keystorePass)
        val keyManagerConfig = KeyManagerConfig().withKeyStoreConfigs(List(keyStoreConfig))

        val sslConfigSettings = SSLConfigSettings().withKeyManagerConfig(keyManagerConfig)
        AhcWSClientConfig(
          wsClientConfig = defaultWsClientConfig.copy(ssl = sslConfigSettings)
        )
      } else {
        val loose = SSLLooseConfig().withAcceptAnyCertificate(true)
        val sslConfigSettings = SSLConfigSettings().withLoose(loose)

        AhcWSClientConfig(
          wsClientConfig = defaultWsClientConfig.copy(ssl = sslConfigSettings)
        )
      }
    } else {
      AhcWSClientConfig(
        wsClientConfig = defaultWsClientConfig
      )
    }
  }

}

