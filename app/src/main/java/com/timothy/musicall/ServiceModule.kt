package com.timothy.musicall

import android.app.Application
import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Room
import android.arch.persistence.room.migration.Migration
import com.timothy.spotifyarchitecture.SpotifyRepository
import com.timothy.spotifyarchitecture.retrofit.SpotifyCreator
import com.timothy.spotifyarchitecture.retrofit.SpotifyService
import com.timothy.spotifyarchitecture.retrofit.SpotifyServiceAuth
import com.timothy.spotifyarchitecture.room.SpotifyDao
import com.timothy.spotifyarchitecture.room.SpotifyDatabase
import com.timothy.spotifyarchitecture.room.SpotifyModule
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
	    return Room.databaseBuilder(application, ServiceDatabase::class.java, "services.db")
			    .addMigrations(Migrators.MIGRATOR_1_2, Migrators.MIGRATOR_2_3, Migrators
					    .MIGRATOR_3_4).build()
    }
}

private class Migrators {
	val migrations = listOf<Migration>(MIGRATOR_1_2, MIGRATOR_2_3, MIGRATOR_3_4)
	
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