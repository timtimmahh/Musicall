package com.timothy.musicall;

import android.app.Application;

import com.timothy.musicall.services.database.DaggerServiceComponent;
import com.timothy.musicall.services.database.ServiceComponent;
import com.timothy.musicall.services.database.ServiceModule;

/**
 * Created by tim on 5/30/17.
 */

public class App extends Application {

    ServiceComponent serviceComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        serviceComponent = DaggerServiceComponent.builder()
                .serviceModule(new ServiceModule(this))
                .build();
    }
}
