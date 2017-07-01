package com.timothy.spotifyarchitecture.viewmodels

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.preference.PreferenceManager
import android.util.Log

import com.timothy.spotifyarchitecture.SpotifyRepository
import com.timothy.spotifyarchitecture.livedata.SpotifyUserLiveData
import com.timothy.spotifyarchitecture.livedata.TokenLiveData
import com.timothy.spotifyarchitecture.remote.Status

import javax.inject.Inject

/**
 * Created by tim on 5/30/17.
 */

class SpotifyViewModel
@Inject
constructor(application: Application, var spotifyRepository: SpotifyRepository) : AndroidViewModel(application) {
    val me = SpotifyUserLiveData.get()

    init {
        init()
    }

    private fun init() {
        val preferences = PreferenceManager.getDefaultSharedPreferences(getApplication<Application>())
        val userId = preferences.getString("SpotifyUser", "")
        Log.d("SpotifyViewModel", "UserId:" + userId)
        authToken.addSource(me) { spotifyUserResource ->
            if (spotifyUserResource != null)
                Log.d("SpotifyViewModel", "onMeChanged - " + spotifyUserResource.status + ": " + if (spotifyUserResource.data != null && spotifyUserResource.status == Status.SUCCESS) spotifyUserResource.data.access_token else spotifyUserResource.message)
            if (spotifyUserResource != null && spotifyUserResource.data != null && spotifyUserResource.status == Status.SUCCESS) {
                if (preferences.getString("SpotifyUser", "")!!.isEmpty()) {
                    preferences.edit().putString("SpotifyUser", spotifyUserResource.data.id).apply()
                    Log.d("SpotifyViewModel", "SavedUserId")
                }
                if (authToken.data() != null) {
                    if (authToken.data()!!.userId.isEmpty() || authToken.data()?.userId != spotifyUserResource.data.id) {
                        authToken.data()!!.userId = spotifyUserResource.data.id
                        spotifyRepository.saveToken(authToken)
                        authToken.setValue(authToken.value)
                    }
                }
            }
        }
        me.addSource(authToken) { tokenResource ->
            if (tokenResource != null)
                Log.d("SpotifyViewModel", "onTokenChanged - " + tokenResource.status + ": " + if (tokenResource.data != null && tokenResource.status == Status.SUCCESS) tokenResource.data.access_token else tokenResource.message)
            if (tokenResource != null && tokenResource.data != null && tokenResource.status == Status.SUCCESS) {
                if (me.data() != null) {
                    if (me.data()!!.access_token != tokenResource.data.access_token) {
                        me.data()!!.access_token = tokenResource.data.access_token
                        spotifyRepository.updateMe(me)
                        me.setValue(me.value)
                    }
                } else
                    loadMe(accessToken = tokenResource.data.access_token)
            }
        }
        if (userId?.isNotEmpty() != null) {
            loadAuthToken(userId)
            Log.d("SpotifyViewModel", "loading Spotify User data")
        }
    }

    fun loadAuthToken(id: String = "", accessToken: String = "", code: String = ""): TokenLiveData {
        return spotifyRepository.getOAuthToken(id, accessToken, code)
    }

    val authToken: TokenLiveData
        get() = me.userToken

    fun loadMe(userId: String = "", accessToken: String = ""): SpotifyUserLiveData {
        return spotifyRepository.getMe(userId, accessToken)
    }


}
