package com.timothy.spotifyarchitecture.viewmodels

import android.app.Activity
import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.content.Intent
import android.preference.PreferenceManager
import com.spotify.sdk.android.authentication.AuthenticationClient
import com.spotify.sdk.android.authentication.AuthenticationRequest
import com.spotify.sdk.android.authentication.AuthenticationResponse
import com.timothy.spotifyarchitecture.*
import com.timothy.spotifyarchitecture.entities.Token
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
	
	    authToken.addSource(spotifyUser) {
            if (isNull(authToken.value) || isNull(it)) return@addSource
		    if (it !!.id != authToken.value !!.userId) {
			    authToken.value !!.userId = it.id
			    spotifyRepository.obtainTokenResource().updateResultResource(authToken.value !!)
		    }
		    if (it.accessToken != authToken.value !!.accessToken) {
			    it.accessToken = authToken.value !!.accessToken
			    spotifyRepository.obtainMeResource().updateResultResource(it)
		    }
        }
        spotifyUser.addSource(authToken) {
	        it?.let {
		        if (isNull(spotifyUser.value)) {
			        loadMe(it.userId, it.accessToken); return@addSource
		        }
		        if (it.userId != spotifyUser.value !!.id) {
			        it.userId = spotifyUser.value !!.id
			        spotifyRepository.obtainTokenResource().updateResultResource(it)
		        }
		        if (it.accessToken != spotifyUser.value !!.accessToken) {
			        spotifyUser.value !!.accessToken = it.accessToken
			        spotifyRepository.obtainMeResource().updateResultResource(spotifyUser.value !!)
		        }
	        }
        }
        if (notEmpty(userId)) {
            loadAuthToken(id = userId)
	        log("loading Spotify Token data")
        }
    }
	
	fun loadAuthLogin(contextActivity: Activity, retry: Boolean = false) {
		val builder = AuthenticationRequest.Builder(Auth.SPOTIFY_CLIENT_ID, AuthenticationResponse.Type.CODE, Auth.REDIRECT_URI)
				.setScopes(arrayOf("playlist-read-private", "playlist-read-collaborative", "playlist-modify-public", "playlist-modify-private", "streaming", "user-follow-modify", "user-follow-read", "user-library-read", "user-library-modify", "user-read-private", "user-read-birthdate", "user-read-email", "user-top-read"))
		val request = builder.build()
		if (isAppInstalled("com.spotify.music", contextActivity.packageManager) && ! retry)
			AuthenticationClient.openLoginActivity(contextActivity, Auth.SPOTIFY_REQUEST_CODE, request)
		else AuthenticationClient.openLoginInBrowser(contextActivity, request)
	}
	
	fun authLoginResponse(resultCode: Int, data: Intent, contextActivity: Activity): TokenLiveData {
		val response = AuthenticationClient.getResponse(resultCode, data)
		when (response.type) {
			AuthenticationResponse.Type.CODE -> {
				log("Code: ${response.code}")
				return loadAuthToken(code = response.code)
			}
			AuthenticationResponse.Type.TOKEN -> {
				log("Token: ${response.accessToken}")
				spotifyRepository.obtainTokenResource().saveResource(Token(accessToken = response.accessToken, scope = arrayOf("playlist-read-private", "playlist-read-collaborative", "playlist-modify-public", "playlist-modify-private", "streaming", "user-follow-modify", "user-follow-read", "user-library-read", "user-library-modify", "user-read-private", "user-read-birthdate", "user-read-email", "user-top-read").toString(),
				                                                           expiresIn = response.expiresIn.toLong(), id = 0))
			}
			AuthenticationResponse.Type.EMPTY -> {
				log("LoginActivity stopped before completing")
			}
			AuthenticationResponse.Type.ERROR -> {
				log("Error authenticating user: ${response.error}"); loadAuthLogin(contextActivity, true)
			}
			AuthenticationResponse.Type.UNKNOWN -> {
				log("Unknown authentication error: ${response.error}"); loadAuthLogin(contextActivity, true)
			}
			else -> log("Null authentication response type")
		}
		return authToken
	}

    fun loadAuthToken(id: String = "", accessToken: String = "", code: String = ""): TokenLiveData {
        log("loadingToken...")
	    val tokenResourceObj = spotifyRepository.obtainTokenResource(id, accessToken, code) { authToken.removeSource(it) }
	    authToken.addSource(tokenResourceObj.obtainResource()) { tokenResource ->
            log(toString(tokenResource), "loadAuthTokenObserver")
            when (tokenResource?.status) {
	            Status.SUCCESS, Status.LOADING -> if (notNullEmpty(tokenResource.data?.accessToken) && tokenResource.data != authToken.value) authToken.value = tokenResource.data else log("Resource: ${tokenResource.status} has null data", "loadAuthTokenObserver")
                Status.ERROR -> log("Error loading resource: ${tokenResource.message}")
                else -> log("Resource is null")
            }
        }
	    log("tokenResource added as authToken source")
        return authToken
    }

    fun loadMe(userId: String = "", accessToken: String = ""): SpotifyUserLiveData {
	    log("loading user...")
	    spotifyUser.addSource(spotifyRepository.obtainMeResource(userId, accessToken) { spotifyUser.removeSource(it) }.obtainResource()) { userResource ->
		    log(toString(userResource), "loadMeObserver")
            when (userResource?.status) {
	            Status.SUCCESS, Status.LOADING -> if (notNull(userResource.data) && userResource.data != spotifyUser.value) spotifyUser.value = userResource.data else log("Resource: ${userResource.status} has null data", "loadAuthTokenObserver")
                Status.ERROR -> log("Error loading resource: ${userResource.message}")
                else -> log("Resource is null")
            }
        }
        return spotifyUser
    }
	
	fun loadMyAlbums() {
		log("loading albums...")
		/*spotifyUser.addSource(spotifyRepository.obtainMyAlbums(spotifyUser.value?.id!!){ spotifyUser.removeSource(it) }.obtainResource()) { userResource ->
			log(toString(userResource), "loadAlbumsObserver")
			when (userResource?.status) {
				Status.SUCCESS, Status.LOADING -> if (notNull(userResource.data)) log("Resource: ${userResource.status} has null data", "loadAuthTokenObserver")
				Status.ERROR -> log("Error loading resource: ${userResource.message}")
				else -> log("Resource is null")
			}
		}*/
	}
	
	val authToken: TokenLiveData
		get() = TokenLiveData.tInstance
	
	val spotifyUser: SpotifyUserLiveData
		get() = SpotifyUserLiveData.uInstance
	
}
