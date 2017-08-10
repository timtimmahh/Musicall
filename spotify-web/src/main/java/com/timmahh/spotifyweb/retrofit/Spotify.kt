package com.timmahh.spotifyweb.retrofit

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

/**
 * Creates and configures a REST adapter for Spotify Web API.

 * Basic usage:
 * SpotifyService spotifyService = Spotify.createAuthenticatedService(access_token);

 * Access token is optional for certain endpoints
 * so if you know you'll only use the ones that don't require authorisation
 * you can use unauthenticated service:

 * SpotifyService spotifyService = Spotify.createNotAuthenticatedService()

 * Call<Album> call = spotifyService.getAlbum("2dIGnmEIy1WZIcZCFSj6i8");
 * Response<Album> response = call.execute();
 * Album album = response.body();
</Album></Album> */
open class Spotify {


    /**
     * The request interceptor that will add the header with OAuth
     * token to every request made with the wrapper.
     */
    class ApiAuthenticator(private val mAccessToken: String?) : Interceptor {

        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {
            val request = chain.request()
            if (mAccessToken != null) {
                val authRequest = request.newBuilder()
                        .addHeader("Authorization", "Bearer " + mAccessToken)
                        .build()
                return chain.proceed(authRequest)
            }
            return chain.proceed(request)
        }
    }

    companion object {

        fun createAuthenticatedService(accessToken: String): SpotifyService {

            val build = Retrofit.Builder()
                    .client(createHttpClient(accessToken))
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(Config.API_URL)
                    .build()

            return build.create(SpotifyService::class.java)
        }

        fun createNotAuthenticatedService(): SpotifyService {

            val build = Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(Config.API_URL)
                    .build()

            return build.create(SpotifyService::class.java)
        }


        fun createHttpClient(accessToken: String): OkHttpClient {
            return OkHttpClient.Builder()
                    .addInterceptor(ApiAuthenticator(accessToken))
                    .build()
        }
    }
}
