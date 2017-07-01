package com.timothy.spotifyarchitecture.retrofit

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

abstract class SpotifyCallback<T> : Callback<T> {

    abstract fun onResponse(call: Call<T>, response: Response<T>, payload: T)

    abstract fun onFailure(call: Call<T>, error: SpotifyError)

    override fun onResponse(call: Call<T>, response: Response<T>) {
        if (response.isSuccessful) {
            onResponse(call, response, response.body())
        } else {
            onFailure(call, SpotifyError.fromResponse(response))
        }
    }

    override fun onFailure(call: Call<T>, t: Throwable) {
        onFailure(call, SpotifyError(t))
    }
}
