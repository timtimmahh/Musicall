package com.timothy.spotifyarchitecture.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.timothy.spotifyarchitecture.SpotifyRepository;
import com.timothy.spotifyarchitecture.entities.SpotifyUser;
import com.timothy.spotifyarchitecture.entities.Token;
import com.timothy.spotifyarchitecture.remote.Resource;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by tim on 5/30/17.
 */

public class SpotifyViewModel extends ViewModel {
    private final SpotifyRepository spotifyRepository;
    private MutableLiveData mediator = new MediatorLiveData();
    private LiveData<Resource<List<Token>>> authToken;
    private LiveData<Resource<SpotifyUser>> me;

    @Inject
    public SpotifyViewModel(SpotifyRepository spotifyRepository) {
        this.spotifyRepository = spotifyRepository;
    }

    public LiveData<Resource<List<Token>>> loadAuthToken(String authorizationCode) {
        return authToken = spotifyRepository.getOAuthToken(authorizationCode);
    }

    public LiveData<Resource<List<Token>>> getAuthToken() {
        return this.authToken;
    }

    public LiveData<Resource<SpotifyUser>> loadMe() {
        return me = spotifyRepository.getMe(me);
    }

    public LiveData<Resource<SpotifyUser>> getMe() {
        return me;
    }

}
