package com.timothy.spotifyarchitecture.retrofit

import java.io.IOException

import retrofit2.Response

class SpotifyError : Exception {

    constructor(detailMessage: String) : super(detailMessage) {}

    constructor(cause: Throwable) : super(cause) {}

    companion object {

        fun <T> fromResponse(response: Response<T>): SpotifyError {

            var error: String = ""
            try {
                error = response.errorBody().string()
            } catch (e: IOException) {
                e.printStackTrace()
            }

            if (error.isNotEmpty()) {
                return SpotifyError(error)
            } else {
                return SpotifyError(Throwable("Can't read error response"))
            }
        }
    }
}
