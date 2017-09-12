package com.timmahh.musicall

import com.timmahh.spotifyweb.presenters.SpotifyDrawerLayout
import dagger.Component
import javax.inject.Singleton

/**
 * Creates a Dagger 2.0 component interface to inject MainActivity
 */
@Singleton
@Component(modules = arrayOf(ServiceModule::class))
interface ServiceComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(spotifyDrawerLayout: SpotifyDrawerLayout)
}
