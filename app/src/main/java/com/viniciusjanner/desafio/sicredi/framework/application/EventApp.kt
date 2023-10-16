package com.viniciusjanner.desafio.sicredi.framework.application

import androidx.multidex.MultiDexApplication
import com.google.android.gms.security.ProviderInstaller
import dagger.hilt.android.HiltAndroidApp
import javax.net.ssl.SSLContext

@HiltAndroidApp
class EventApp : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        try {
            instance = this
            //
            // O Google Play instalará o OpenSSL mais recente
            //
            ProviderInstaller.installIfNeeded(applicationContext)
            val sslContext: SSLContext = SSLContext.getInstance("TLSv1.2")
            sslContext.init(null, null, null)
            sslContext.createSSLEngine()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    companion object {
        lateinit var instance: EventApp
            private set
    }
}
