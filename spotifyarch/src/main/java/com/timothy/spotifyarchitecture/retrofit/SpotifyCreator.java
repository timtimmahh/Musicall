package com.timothy.spotifyarchitecture.retrofit;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by tim on 5/28/17.
 */

public class SpotifyCreator extends Spotify {

    public static SpotifyService createAuthenticatedService(ApiAuthenticator authenticator) {
        Retrofit build = (new Retrofit.Builder()).client(createHttpClient(authenticator)).addConverterFactory(GsonConverterFactory.create()).baseUrl("https://api.spotify.com/v1/").build();
        return build.create(SpotifyService.class);
    }

    public static SpotifyService createNotAuthenticatedService() {
        Retrofit build = (new Retrofit.Builder()).client(createNoAuthHttpClient()).addConverterFactory(GsonConverterFactory.create()).baseUrl("https://api.spotify.com/v1/").build();
        return build.create(SpotifyService.class);
    }

    public static SpotifyServiceAuth createServiceAuthenticator() {
        Retrofit build = (new Retrofit.Builder()).client(createNoAuthHttpClient()).addConverterFactory(GsonConverterFactory.create()).baseUrl(SpotifyServiceAuth.SPOTIFY_AUTH_API_ENDPOINT).build();
        return build.create(SpotifyServiceAuth.class);
    }

    public static OkHttpClient createHttpClient(ApiAuthenticator authenticator) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        return (new okhttp3.OkHttpClient.Builder()).addInterceptor(interceptor).addInterceptor(authenticator).build();
    }

    public static OkHttpClient createNoAuthHttpClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        return (new okhttp3.OkHttpClient.Builder()).addInterceptor(interceptor).build();
    }

    public static ApiAuthenticator createApiAuthenticator(String token) {
        return new ApiAuthenticator(token);
    }

    public static class ApiAuthenticator implements Interceptor {
        private String mAccessToken;

        public ApiAuthenticator(String accessToken) {
            this.mAccessToken = accessToken;
        }

        public void setAccessToken(String accessToken) {
            this.mAccessToken = accessToken;
        }

        public Response intercept(Interceptor.Chain chain) throws IOException {
            Request request = chain.request();
            if(this.mAccessToken != null) {
                Request authRequest = request.newBuilder().addHeader("Authorization", "Bearer " + this.mAccessToken).build();
                return chain.proceed(authRequest);
            } else {
                return chain.proceed(request);
            }
        }
    }

}
