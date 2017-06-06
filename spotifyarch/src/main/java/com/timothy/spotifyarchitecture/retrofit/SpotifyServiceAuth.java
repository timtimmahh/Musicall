package com.timothy.spotifyarchitecture.retrofit;

import com.timothy.spotifyarchitecture.entities.Token;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by tim on 4/14/17.
 */

public interface SpotifyServiceAuth {
    String SPOTIFY_AUTH_API_ENDPOINT = "https://accounts.spotify.com/";

    @POST("api/token")
    @FormUrlEncoded
    Call<Token> getToken(@FieldMap Map<String, Object> body);

    @POST("api/token")
    @FormUrlEncoded
    Call<Token> getRefreshToken(@FieldMap Map<String, Object> body);

}
