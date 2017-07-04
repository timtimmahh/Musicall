package com.timothy.spotifyarchitecture.livedata

import android.arch.lifecycle.MediatorLiveData
import com.timothy.spotifyarchitecture.entities.SpotifyUser

/**
 * LiveData implementation to create a MediatorLiveData<SpotifyUser>
 */

class SpotifyUserLiveData private constructor() : MediatorLiveData<SpotifyUser>() {

    companion object {
        @JvmField
        var uInstance: SpotifyUserLiveData = SpotifyUserLiveData()
    }
}