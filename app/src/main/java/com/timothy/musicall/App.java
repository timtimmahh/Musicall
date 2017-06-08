package com.timothy.musicall;

import android.app.Application;

import com.timothy.spotifyarchitecture.room.SpotifyModule;

public class App extends Application {

    ServiceComponent serviceComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        serviceComponent = DaggerServiceComponent.builder()
                .serviceModule(new ServiceModule(this))
                .build();
    }

    public ServiceComponent getServiceComponent() {
        return serviceComponent;
    }
}
