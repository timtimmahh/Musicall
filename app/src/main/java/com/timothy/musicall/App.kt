package com.timothy.musicall

import android.app.Application
class App : Application() {

    lateinit var serviceComponent: ServiceComponent

    override fun onCreate() {
        super.onCreate()
        serviceComponent = DaggerServiceComponent.builder()
                .serviceModule(ServiceModule(this))
                .build()
    }
}
