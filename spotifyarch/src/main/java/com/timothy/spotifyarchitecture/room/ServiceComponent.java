package com.timothy.spotifyarchitecture.room;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by tim on 5/31/17.
 */
@Singleton
@Component(modules = SpotifyModule.class)
public interface ServiceComponent {
    //void inject(MainActivity mainActivity);
}
