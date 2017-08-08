package com.timothy.spotifyarchitecture.room

import com.timothy.spotifyarchitecture.SpotifyRepository
import com.timothy.spotifyarchitecture.retrofit.SpotifyCreator
import com.timothy.spotifyarchitecture.retrofit.SpotifyService
import com.timothy.spotifyarchitecture.retrofit.SpotifyServiceAuth

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
