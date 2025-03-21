package kz.sdu.edu

import android.app.Application
import kz.sdu.edu.di.authModule
import kz.sdu.edu.di.mainModule
import kz.sdu.edu.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApp)
            modules(listOf(
                networkModule,
                authModule,
                mainModule
            ))
        }
    }
}