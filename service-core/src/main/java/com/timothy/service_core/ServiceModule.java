package com.timothy.service_core;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.timothy.spotifyarchitecture.room.SpotifyDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by tim on 6/6/17.
 */
@Module
public class ServiceModule {

    Application application;

    public ServiceModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    ServiceDatabase provideServiceDatabase() {
        return Room.databaseBuilder(application, ServiceDatabase.class, "services.db").build();
    }

    @Provides @Singleton
    SpotifyDao provideServiceDao(ServiceDatabase serviceDatabase) {
        return serviceDatabase.serviceDao();
    }

}
