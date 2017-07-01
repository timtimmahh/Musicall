package com.timothy.spotifyarchitecture.livedata

import android.arch.lifecycle.Observer

import com.timothy.spotifyarchitecture.entities.SpotifyUser
import com.timothy.spotifyarchitecture.entities.Token
import com.timothy.spotifyarchitecture.remote.Resource
import com.timothy.spotifyarchitecture.remote.Status

/**
 * Created by tim on 6/8/17.
 */

class SpotifyUserLiveData private constructor() : ResourceLiveData<SpotifyUser>() {
    val userToken: TokenLiveData

    init {
        userToken = TokenLiveData()
    }

    companion object {
        private var uInstance: SpotifyUserLiveData? = null

        fun get(): SpotifyUserLiveData {
            if (uInstance == null)
                uInstance = SpotifyUserLiveData()
            return uInstance as SpotifyUserLiveData
        }
    }
}
