package com.timothy.musicall

import android.app.Application
import android.arch.persistence.room.Room
import com.timothy.spotifyarchitecture.SpotifyRepository
import com.timothy.spotifyarchitecture.retrofit.SpotifyCreator
import com.timothy.spotifyarchitecture.retrofit.SpotifyService
import com.timothy.spotifyarchitecture.retrofit.SpotifyServiceAuth
import com.timothy.spotifyarchitecture.room.SpotifyDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Dagger 2.0 module class
 */
@Module
class ServiceModule(var application: Application) {

    @Provides
    @Singleton
    fun provideApiAuthenticator(): SpotifyCreator.ApiAuthenticator {
        return SpotifyCreator.createApiAuthenticator()
    }

    @Provides
    @Singleton
    fun provideSpotifyService(apiAuthenticator: SpotifyCreator.ApiAuthenticator): SpotifyService {
        return SpotifyCreator.createAuthenticatedService(apiAuthenticator)
    }

    @Provides
    @Singleton
    fun provideSpotifyServiceAuth(): SpotifyServiceAuth {
        return SpotifyCreator.createServiceAuthenticator()
    }

    @Provides
    @Singleton
    fun provideSpotifyDao(spotifyDatabase: ServiceDatabase): SpotifyDao {
        return spotifyDatabase.spotifyDao()
    }

    @Provides
    @Singleton
    fun provideSpotifyRepository(spotifyDao: SpotifyDao, spotifyService: SpotifyService, spotifyServiceAuth: SpotifyServiceAuth, apiAuthenticator: SpotifyCreator.ApiAuthenticator): SpotifyRepository {
        return SpotifyRepository(spotifyDao, spotifyService, spotifyServiceAuth, apiAuthenticator)
    }

    @Provides
    @Singleton
    fun provideApplication(): Application {
        return application
    }

    @Provides
    @Singleton
    fun provideServiceDatabase(): ServiceDatabase {
        return Room.databaseBuilder(application, ServiceDatabase::class.java, "services.db").build()
    }

}
