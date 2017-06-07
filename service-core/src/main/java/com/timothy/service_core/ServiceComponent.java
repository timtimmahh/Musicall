package com.timothy.service_core;

import com.timothy.spotifyarchitecture.room.SpotifyModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by tim on 5/31/17.
 */
@Singleton
@Component(modules = ServiceModule.class)
public interface ServiceComponent {
    //void inject(MainActivity mainActivity);
}
