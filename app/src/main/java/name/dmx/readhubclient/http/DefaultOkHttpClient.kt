package com.hzzh.baselibrary.net

import android.content.Context
import name.dmx.readhubclient.R
import okhttp3.OkHttpClient
import java.security.KeyStore
import java.security.SecureRandom
import java.security.cert.CertificateFactory
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory

/**
 * Created by dmx on 2017/8/11.
 */
object DefaultOkHttpClient {
    private val DEFAULT_TIME_OUT = 10//默认的超时时间5秒钟

    fun getOkHttpClient(context: Context): OkHttpClient {
        val builder = OkHttpClient.Builder()
        builder.connectTimeout(DEFAULT_TIME_OUT.toLong(), TimeUnit.SECONDS)
        builder.readTimeout(DEFAULT_TIME_OUT.toLong(), TimeUnit.SECONDS)
        builder.writeTimeout(DEFAULT_TIME_OUT.toLong(), TimeUnit.SECONDS)

        setCertificates(context, builder)
        return builder.build()
    }

    private fun setCertificates(context: Context, builder: OkHttpClient.Builder) {
        try {
            val certificateFactory = CertificateFactory.getInstance("X.509")
            val input = context.resources.openRawResource(R.raw.readhub)
            val trustStore = KeyStore.getInstance(KeyStore
                    .getDefaultType())
            trustStore.load(null)
            trustStore.setCertificateEntry("", certificateFactory.generateCertificate(input))
            val sslContext = SSLContext.getInstance("TLS")

            val trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())

            trustManagerFactory.init(trustStore)
            sslContext.init(
                    null,
                    trustManagerFactory.trustManagers,
                    SecureRandom()
            )
            builder.sslSocketFactory(sslContext.socketFactory)


        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
}