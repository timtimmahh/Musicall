package com.timothy.musicall

import android.app.Application
import com.facebook.stetho.Stetho

class App : Application() {

    lateinit var serviceComponent: ServiceComponent

    override fun onCreate() {
        super.onCreate()
        serviceComponent = DaggerServiceComponent.builder()
                .serviceModule(ServiceModule(this))
                .build()
	    Stetho.initialize(Stetho.newInitializerBuilder(this)
			                      .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
			                      .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
			                      .build())
    }
}
