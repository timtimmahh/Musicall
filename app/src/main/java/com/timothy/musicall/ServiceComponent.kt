package com.timothy.musicall

import dagger.Component
import javax.inject.Singleton

/**
 * Creates a Dagger 2.0 component interface to inject MainActivity
 */
@Singleton
@Component(modules = arrayOf(ServiceModule::class))
interface ServiceComponent {
    fun inject(mainActivity: MainActivity)
}
