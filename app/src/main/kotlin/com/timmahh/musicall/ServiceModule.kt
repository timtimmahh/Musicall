package com.timmahh.musicall

import android.app.Application
import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Room
import android.arch.persistence.room.migration.Migration
import com.timmahh.spotifyweb.SpotifyRepository
import com.timmahh.spotifyweb.retrofit.SpotifyCreator
import com.timmahh.spotifyweb.retrofit.SpotifyService
import com.timmahh.spotifyweb.retrofit.SpotifyServiceAuth
import com.timmahh.spotifyweb.room.SpotifyDao
import com.timmahh.spotifyweb.room.SpotifyDatabase
import com.timmahh.spotifyweb.room.SpotifyModule
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Dagger 2.0 module class
 */
@Module
class ServiceModule(var application: Application) : SpotifyModule {
    @Provides
    @Singleton
    override fun provideSpotifyDatabase(): SpotifyDatabase {
	    return provideServiceDatabase()
    }
	
	@Provides
	@Singleton
	override fun provideApiAuthenticator(): SpotifyCreator.ApiAuthenticator {
        return SpotifyCreator.createApiAuthenticator()
    }

    @Provides
    @Singleton
    override fun provideSpotifyService(apiAuthenticator: SpotifyCreator.ApiAuthenticator): SpotifyService {
        return SpotifyCreator.createAuthenticatedService(apiAuthenticator)
    }

    @Provides
    @Singleton
    override fun provideSpotifyServiceAuth(): SpotifyServiceAuth {
        return SpotifyCreator.createServiceAuthenticator()
    }

    @Provides
    @Singleton
    override fun provideSpotifyDao(spotifyDatabase: SpotifyDatabase): SpotifyDao {
        return spotifyDatabase.spotifyDao()
    }

    @Provides
    @Singleton
    override fun provideSpotifyRepository(spotifyDao: SpotifyDao, spotifyService: SpotifyService, spotifyServiceAuth: SpotifyServiceAuth, apiAuthenticator: SpotifyCreator.ApiAuthenticator): SpotifyRepository {
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
	    return Room.databaseBuilder(application, ServiceDatabase::class.java, "services.db").fallbackToDestructiveMigration()
			    .build()
    }
}

private class Migrators {
	object MIGRATOR_1_2 : Migration(1, 2) {
		override fun migrate(database: SupportSQLiteDatabase?) {
			//TODO for future database changes
		}
	}
	
	object MIGRATOR_2_3 : Migration(2, 3) {
		override fun migrate(database: SupportSQLiteDatabase?) {
			//TODO for future database changes
		}
	}
	
	object MIGRATOR_3_4 : Migration(3, 4) {
		override fun migrate(database: SupportSQLiteDatabase?) {
			//TODO for future database changes
		}
	}
}