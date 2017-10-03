package com.timmahh.spotifyweb.retrofit


import com.timmahh.spotifyweb.retrofit.models.Token
import retrofit2.Call
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * Retrofit service to authenticate and retrieve OAuth token's from Spotify
 */

interface SpotifyServiceAuth {

    @POST("api/token")
    @FormUrlEncoded
    fun getToken(@FieldMap body: Map<String, String>): Call<Token>

    companion object {
        val SPOTIFY_AUTH_API_ENDPOINT = "https://accounts.spotify.com/"
    }

}
