package com.timothy.spotifyarchitecture.room;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.timothy.spotifyarchitecture.SpotifyRepository;
import com.timothy.spotifyarchitecture.retrofit.SpotifyCreator;
import com.timothy.spotifyarchitecture.retrofit.SpotifyService;
import com.timothy.spotifyarchitecture.retrofit.SpotifyServiceAuth;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by tim on 5/31/17.
 */
@Module
public class SpotifyModule {

    Application application;

    public SpotifyModule(Application application) {
        this.application = application;
    }

    @Provides @Singleton
    SpotifyCreator.ApiAuthenticator provideApiAuthenticator() {
        return SpotifyCreator.createApiAuthenticator(null);
    }

    @Provides @Singleton
    SpotifyService provideSpotifyService(SpotifyCreator.ApiAuthenticator apiAuthenticator) {
        return SpotifyCreator.createAuthenticatedService(apiAuthenticator);
    }

    @Provides @Singleton
    SpotifyServiceAuth provideSpotifyServiceAuth() {
        return SpotifyCreator.createServiceAuthenticator();
    }

    @Provides @Singleton
    SpotifyRepository provideSpotifyRepository(SpotifyDao serviceDao, SpotifyService spotifyService, SpotifyServiceAuth spotifyServiceAuth, SpotifyCreator.ApiAuthenticator apiAuthenticator) {
        return new SpotifyRepository(serviceDao, spotifyService, spotifyServiceAuth, apiAuthenticator);
    }

}
