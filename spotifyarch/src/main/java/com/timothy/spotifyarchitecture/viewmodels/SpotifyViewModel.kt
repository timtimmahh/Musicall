package com.timothy.spotifyarchitecture.viewmodels

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.preference.PreferenceManager
import com.timothy.spotifyarchitecture.*
import com.timothy.spotifyarchitecture.livedata.SpotifyUserLiveData
import com.timothy.spotifyarchitecture.livedata.TokenLiveData
import com.timothy.spotifyarchitecture.remote.Status
import javax.inject.Inject

/**
 * ViewModel implementation to create a Spotify view instance
 */

class SpotifyViewModel
@Inject
constructor(application: Application, private var spotifyRepository: SpotifyRepository) : AndroidViewModel(application) {

    init {
        init()
    }

    private fun init() {
        val preferences = PreferenceManager.getDefaultSharedPreferences(getApplication())
        val userId = preferences.getString("SpotifyUser", "")
        log("UserId:" + userId)
        log("User:" + toString(spotifyUser))
        log("Token:" + toString(authToken))
        /*authToken.addSource(spotifyUser) { spotifyUserResource ->
            if (notNull(spotifyUserResource))
                log("onMeChanged - " + spotifyUserResource!!.status + ": " + if (notNull(spotifyUserResource.data) && spotifyUserResource.status == Status.SUCCESS) spotifyUserResource.data!!.access_token else spotifyUserResource.message)
            if (notNull(spotifyUserResource?.data) && spotifyUserResource!!.status == Status.SUCCESS) {
                if (empty(preferences.getString("SpotifyUser", ""))) {
                    preferences.edit().putString("SpotifyUser", spotifyUserResource.data!!.id).apply()
                    log("SavedUserId")
                }
                if (notNull(authToken.data())) {
                    if (empty(authToken.data()!!.userId) || authToken.data()!!.userId != spotifyUserResource.data!!.id) {
                        authToken.data()!!.userId = spotifyUserResource.data!!.id
                        spotifyRepository.saveToken(authToken)
                        authToken.value = authToken.value
                    }
                }
            }
        }
        spotifyUser.addSource(authToken) { tokenResource ->
            if (notNull(tokenResource))
                log("onTokenChanged - " + tokenResource!!.status + ": " + if (notNull(tokenResource.data) && tokenResource.status == Status.SUCCESS) tokenResource.data!!.access_token else tokenResource.message)
            if (notNull(tokenResource?.data) && tokenResource!!.status == Status.SUCCESS) {
                if (notNull(spotifyUser.data())) {
                    if (spotifyUser.data()!!.access_token != tokenResource.data!!.access_token) {
                        spotifyUser.data()!!.access_token = tokenResource.data.access_token
                        spotifyRepository.updateMe(spotifyUser)
                        spotifyUser.value = spotifyUser.value
                    }
                } else
                    loadMe(accessToken = tokenResource.data!!.access_token)
            }
        }*/
        /*authToken.addSource(spotifyUser) {
            if (isNull(authToken.value) || isNull(it)) return@addSource
            if (it!!.id != authToken.value!!.userId)
                authToken.value!!.userId = it.id
            if (it.access_token != authToken.value!!.access_token)
                it.access_token = authToken.value!!.access_token
        }
        spotifyUser.addSource(authToken) {
            if (isNull(it)) return@addSource else if (isNull(spotifyUser.value)) loadMe(it!!.userId, it.access_token)
            if (it!!.userId != spotifyUser.value!!.id)
                it.userId = spotifyUser.value!!.id
            if (it.access_token != spotifyUser.value!!.access_token)
                spotifyUser.value!!.access_token = it.access_token
        }*/
        if (notEmpty(userId)) {
            loadAuthToken(id = userId)
            log("loading Spotify User data")
        }
    }

    fun loadAuthToken(id: String = "", accessToken: String = "", code: String = ""): TokenLiveData {
        log("loadingToken...")
        authToken.addSource(spotifyRepository.getToken(id, accessToken, code)) { tokenResource ->
            log(toString(tokenResource), "loadAuthTokenObserver")
            when (tokenResource?.status) {
                Status.SUCCESS, Status.LOADING -> if (notNull(tokenResource.data) && tokenResource.data != authToken.value) authToken.value = tokenResource.data else log("Resource: ${tokenResource.status} has null data", "loadAuthTokenObserver")
                Status.ERROR -> log("Error loading resource: ${tokenResource.message}")
                else -> log("Resource is null")
            }
            if (tokenResource?.status == Status.SUCCESS && notNull(tokenResource.data))
                authToken.value = tokenResource.data
        }
        return authToken
    }

    val authToken: TokenLiveData
        get() = TokenLiveData.tInstance

    val spotifyUser: SpotifyUserLiveData
        get() = SpotifyUserLiveData.uInstance

    fun loadMe(userId: String = "", accessToken: String = ""): SpotifyUserLiveData {
        spotifyUser.addSource(spotifyRepository.getMe(userId, accessToken)) { userResource ->
            log(toString(), "loadMeObserver")
            when (userResource?.status) {
                Status.SUCCESS, Status.LOADING -> if (notNull(userResource.data)) spotifyUser.value = userResource.data else log("Resource: ${userResource.status} has null data", "loadAuthTokenObserver")
                Status.ERROR -> log("Error loading resource: ${userResource.message}")
                else -> log("Resource is null")
            }
        }
        return spotifyUser
    }


}
