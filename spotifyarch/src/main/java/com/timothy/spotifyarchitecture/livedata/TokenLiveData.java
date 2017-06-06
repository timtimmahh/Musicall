package com.timothy.spotifyarchitecture.livedata;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.support.annotation.MainThread;

import com.timothy.spotifyarchitecture.entities.Token;
import com.timothy.spotifyarchitecture.remote.Resource;

/**
 * Created by tim on 6/4/17.
 */

public class TokenLiveData extends LiveData<Resource<Token>> {
    private static TokenLiveData tInstance;

    @MainThread
    public static TokenLiveData get() {
        if (tInstance == null)
            tInstance = new TokenLiveData();
        return tInstance;
    }

}
