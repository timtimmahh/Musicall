package com.timmahh.spotifyweb.room

import com.timmahh.spotifyweb.SpotifyRepository
import com.timmahh.spotifyweb.retrofit.SpotifyCreator
import com.timmahh.spotifyweb.retrofit.SpotifyService
import com.timmahh.spotifyweb.retrofit.SpotifyServiceAuth

/**
 * Dagger module interface to provide necessary objects upon startup.
 */
interface SpotifyModule {
    
    fun provideSpotifyDatabase(): SpotifyDatabase
    
    fun provideSpotifyDao(spotifyDatabase: SpotifyDatabase): SpotifyDao
    
    fun provideApiAuthenticator(): SpotifyCreator.ApiAuthenticator

    fun provideSpotifyService(apiAuthenticator: SpotifyCreator.ApiAuthenticator): SpotifyService

    fun provideSpotifyServiceAuth(): SpotifyServiceAuth

    fun provideSpotifyRepository(spotifyDao: SpotifyDao, spotifyService: SpotifyService, spotifyServiceAuth: SpotifyServiceAuth, apiAuthenticator: SpotifyCreator.ApiAuthenticator): SpotifyRepository

}
