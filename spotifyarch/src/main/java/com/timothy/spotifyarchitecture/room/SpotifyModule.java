package com.timothy.spotifyarchitecture.room;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;

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
public interface SpotifyModule {

    SpotifyCreator.ApiAuthenticator provideApiAuthenticator();

    SpotifyService provideSpotifyService(SpotifyCreator.ApiAuthenticator apiAuthenticator);

    SpotifyServiceAuth provideSpotifyServiceAuth();

    SpotifyRepository provideSpotifyRepository(SpotifyDao spotifyDao, SpotifyService spotifyService, SpotifyServiceAuth spotifyServiceAuth, SpotifyCreator.ApiAuthenticator apiAuthenticator);

}
