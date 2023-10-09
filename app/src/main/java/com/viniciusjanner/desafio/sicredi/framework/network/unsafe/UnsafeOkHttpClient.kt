package com.viniciusjanner.desafio.sicredi.framework.network.unsafe

import android.annotation.SuppressLint
import android.util.Log
import okhttp3.CipherSuite
import okhttp3.ConnectionSpec
import okhttp3.OkHttpClient
import okhttp3.TlsVersion
import java.security.cert.CertificateException
import java.util.Collections
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

@SuppressLint("CustomX509TrustManager", "TrustAllX509TrustManager")
class UnsafeOkHttpClient {

    companion object {

        private val tag: String = UnsafeOkHttpClient::class.java.simpleName

        fun getUnsafeOkHttpClient(): OkHttpClient.Builder {
            try {
                //
                // Crie um gerenciador de confiança que não valide cadeias de certificados
                //
                val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
                    @Throws(CertificateException::class)
                    override fun checkClientTrusted(chain: Array<java.security.cert.X509Certificate>, authType: String) {
                    }

                    @Throws(CertificateException::class)
                    override fun checkServerTrusted(chain: Array<java.security.cert.X509Certificate>, authType: String) {
                    }

                    override fun getAcceptedIssuers(): Array<java.security.cert.X509Certificate> = arrayOf()
                })

                //
                // Instale o gerenciador de confiança totalmente confiável
                //
                val sslContext = SSLContext.getInstance("SSL")
                sslContext.init(null, trustAllCerts, java.security.SecureRandom())

                //
                // Crie um socketFactory SSL com nosso gerenciador totalmente confiável
                //
                val sslSocketFactory = sslContext.socketFactory

                //
                // Builder
                //
                val builder = OkHttpClient.Builder()
                builder.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
                builder.hostnameVerifier { _, _ -> true }

                return builder
            } catch (e: Exception) {
                Log.e(tag, "getUnsafeOkHttpClient: ${e.message}")
                throw RuntimeException(e)
            }
        }

        fun getConnectionSpecsList(): List<ConnectionSpec> {
            try {
                val spec = ConnectionSpec.Builder(ConnectionSpec.COMPATIBLE_TLS)
                    .supportsTlsExtensions(true)
                    .tlsVersions(TlsVersion.TLS_1_2, TlsVersion.TLS_1_1, TlsVersion.TLS_1_0)
                    .cipherSuites(
                        CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256,
                        CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256,
                        CipherSuite.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256,
                        CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA,
                        CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA,
                        CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA,
                        CipherSuite.TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA,
                        CipherSuite.TLS_ECDHE_ECDSA_WITH_RC4_128_SHA,
                        CipherSuite.TLS_ECDHE_RSA_WITH_RC4_128_SHA,
                        CipherSuite.TLS_DHE_RSA_WITH_AES_128_CBC_SHA,
                        CipherSuite.TLS_DHE_DSS_WITH_AES_128_CBC_SHA,
                        CipherSuite.TLS_DHE_RSA_WITH_AES_256_CBC_SHA
                    )
                    .build()

                return Collections.singletonList(spec)
            } catch (e: Exception) {
                Log.e(tag, "getConnectionSpecsList: ${e.message}")
                throw RuntimeException(e)
            }
        }
    }
}
