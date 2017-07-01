package com.timothy.spotifyarchitecture.retrofit

import com.timothy.spotifyarchitecture.entities.Token

import retrofit2.Call
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * Created by tim on 4/14/17.
 */

interface SpotifyServiceAuth {

    @POST("api/token")
    @FormUrlEncoded
    fun getToken(@FieldMap body: Map<String, Any>): Call<Token>

    companion object {
        val SPOTIFY_AUTH_API_ENDPOINT = "https://accounts.spotify.com/"
    }

}
