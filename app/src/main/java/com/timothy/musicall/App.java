package com.timothy.musicall;

import android.app.Application;

public class App extends Application {

//    ServiceComponent serviceComponent;

    @Override
    public void onCreate() {
        super.onCreate();
//        serviceComponent = DaggerServiceComponent.builder()
//                .serviceModule(new ServiceModule(this))
//                .build();
    }
}
