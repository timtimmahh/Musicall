package com.timothy.spotifyarchitecture;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.timothy.spotifyarchitecture.entities.SpotifyUser;
import com.timothy.spotifyarchitecture.entities.Token;
import com.timothy.spotifyarchitecture.remote.NetworkBoundResource;
import com.timothy.spotifyarchitecture.remote.Resource;
import com.timothy.spotifyarchitecture.retrofit.SpotifyCreator;
import com.timothy.spotifyarchitecture.retrofit.SpotifyService;
import com.timothy.spotifyarchitecture.retrofit.SpotifyServiceAuth;
import com.timothy.spotifyarchitecture.retrofit.models.UserPrivate;
import com.timothy.spotifyarchitecture.room.SpotifyDao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;

/**
 * Created by tim on 5/30/17.
 */
@Singleton
public class SpotifyRepository {

    private final SpotifyDao serviceDao;
    private final SpotifyCreator.ApiAuthenticator apiAuthenticator;
    private final SpotifyService spotifyService;
    private final SpotifyServiceAuth spotifyServiceAuth;

    @Inject
    public SpotifyRepository(SpotifyDao serviceDao, SpotifyService spotifyService, SpotifyServiceAuth spotifyServiceAuth, SpotifyCreator.ApiAuthenticator apiAuthenticator) {
        this.serviceDao = serviceDao;
        this.spotifyService = spotifyService;
        this.spotifyServiceAuth = spotifyServiceAuth;
        this.apiAuthenticator = apiAuthenticator;
    }

    public LiveData<Resource<List<Token>>> getOAuthToken(String authorizationCode) {
        return new NetworkBoundResource<List<Token>, Token>() {
            @Override
            protected void saveCallResult(@NonNull Token item) {
                apiAuthenticator.setAccessToken(item.access_token);
                serviceDao.saveToken(Token.createToken(item));
            }

            @NonNull
            @Override
            protected LiveData<List<Token>> loadFromDb() {
                return serviceDao.loadAuthToken();
            }

            @NonNull
            @Override
            protected Call<Token> createCall() {
                Map<String, Object> body = new HashMap<>();
                body.put("grant_type", "authorization_code");
                body.put("code", authorizationCode);
                body.put("redirect_uri", Auth.REDIRECT_URI);
                body.put("client_id", Auth.SPOTIFY_CLIENT_ID);
                body.put("client_secret", Auth.SPOTIFY_CLIENT_SECRET);
                return spotifyServiceAuth.getToken(body);
            }
        }.getAsLiveData();
    }


    public LiveData<Resource<SpotifyUser>> getMe(@NonNull LiveData<Resource<SpotifyUser>> user) {
        return new NetworkBoundResource<SpotifyUser, UserPrivate>() {
            @Override
            protected void saveCallResult(@NonNull UserPrivate item) {
                serviceDao.saveMe(new SpotifyUser(item));
            }

            @NonNull
            @Override
            protected LiveData<SpotifyUser> loadFromDb() {
                return serviceDao.loadMe(user.getValue() != null && user.getValue().data != null ?
                        user.getValue().data.id : null);
            }

            @NonNull
            @Override
            protected Call<UserPrivate> createCall() {
                return spotifyService.getMe();
            }
        }.getAsLiveData();
    }


}
