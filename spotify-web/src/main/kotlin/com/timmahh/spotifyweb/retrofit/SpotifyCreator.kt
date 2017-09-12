package com.timmahh.spotifyweb.retrofit

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

/**
 * A utility class to create retrofit services to access the Spotify Web API
 */

class SpotifyCreator : Spotify() {

    class ApiAuthenticator(var accessToken: String = "") : Interceptor {

        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {
            val request = chain.request()
            if (!this.accessToken.isEmpty()) {
                val authRequest = request.newBuilder().addHeader("Authorization", "Bearer " + this.accessToken).build()
                return chain.proceed(authRequest)
            } else {
                return chain.proceed(request)
            }
        }
    }

    companion object {

        fun createAuthenticatedService(authenticator: ApiAuthenticator): SpotifyService {
            val build = Retrofit.Builder().client(createHttpClient(authenticator)).addConverterFactory(GsonConverterFactory.create()).baseUrl("https://api.spotify.com/v1/").build()
            return build.create(SpotifyService::class.java)
        }

       /* fun createNotAuthenticatedService(): SpotifyService {
            val build = Retrofit.Builder().client(createNoAuthHttpClient()).addConverterFactory(GsonConverterFactory.create()).baseUrl("https://api.spotify.com/v1/").build()
            return build.create(SpotifyService::class.java)
        }*/

        fun createServiceAuthenticator(): SpotifyServiceAuth {
            val build = Retrofit.Builder().client(createNoAuthHttpClient()).addConverterFactory(GsonConverterFactory.create()).baseUrl(SpotifyServiceAuth.SPOTIFY_AUTH_API_ENDPOINT).build()
            return build.create(SpotifyServiceAuth::class.java)
        }

        fun createHttpClient(authenticator: ApiAuthenticator): OkHttpClient {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BASIC
            return okhttp3.OkHttpClient.Builder().addInterceptor(interceptor).addInterceptor(authenticator).build()
        }

        fun createNoAuthHttpClient(): OkHttpClient {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BASIC
            return okhttp3.OkHttpClient.Builder().addInterceptor(interceptor).build()
        }

        fun createApiAuthenticator(token: String = ""): ApiAuthenticator {
            return ApiAuthenticator(token)
        }
    }

}
