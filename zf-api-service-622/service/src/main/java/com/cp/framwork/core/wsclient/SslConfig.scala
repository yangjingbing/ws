package com.cp.framwork.core.wsclient

case class SslConfig(keyStorePath: String, keystorePass: String)

case class ClientConfig(
  useSsl: Boolean,
  requestTimeoutMills: Option[Long] = None,
  keyStorePath: Option[String] = None,
  keystorePass: Option[String] = None
)
