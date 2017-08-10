package com.timmahh.spotifyweb.viewmodels

import android.app.Activity
import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.content.Intent
import com.spotify.sdk.android.authentication.AuthenticationClient
import com.spotify.sdk.android.authentication.AuthenticationRequest
import com.spotify.sdk.android.authentication.AuthenticationResponse
import com.timmahh.spotifyweb.*
import com.timmahh.spotifyweb.livedata.SpotifyUserLiveData
import com.timmahh.spotifyweb.livedata.TokenLiveData
import com.timmahh.spotifyweb.remote.Status
import com.timmahh.spotifyweb.retrofit.models.Token
import javax.inject.Inject

/**
 * ViewModel implementation to create a Spotify view instance
 */
class SpotifyUserViewModel
@Inject
constructor(application: Application, private var spotifyRepository: SpotifyRepository) : AndroidViewModel(application) {

    init {
        init()
    }

    private fun init() {
	    authToken.addSource(spotifyUser) {
            if (isNull(authToken.value) || isNull(it)) return@addSource
		    if (it !!.id != authToken.value !!.user_id) {
			    authToken.value !!.user_id = it.id
			    spotifyRepository.obtainTokenResource().updateResultResource(authToken.value !!)
		    }
		    if (it.token.access_token != authToken.value !!.access_token) {
			    it.token.access_token = authToken.value !!.access_token
			    spotifyRepository.obtainMeResource(accessToken = authToken.value !!).updateResultResource(it)
		    }
        }
        spotifyUser.addSource(authToken) {
	        it?.let {
		        if (isNull(spotifyUser.value)) {
			        loadMe(it.user_id, it); return@addSource
		        }
		        if (it.user_id != spotifyUser.value !!.id) {
			        it.user_id = spotifyUser.value !!.id
			        spotifyRepository.obtainTokenResource().updateResultResource(it)
		        }
		        if (it.access_token != spotifyUser.value !!.token.access_token) {
			        spotifyUser.value !!.token.access_token = it.access_token
			        spotifyRepository.obtainMeResource(accessToken = Token())
					        .updateResultResource(spotifyUser.value !!)
		        }
	        }
        }
	    spotifyUser.value = spotifyRepository.obtainLoggedInUser()
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
				spotifyRepository.obtainTokenResource().saveResource(Token(access_token = response.accessToken, scope = arrayOf("playlist-read-private", "playlist-read-collaborative", "playlist-modify-public", "playlist-modify-private", "streaming", "user-follow-modify", "user-follow-read", "user-library-read", "user-library-modify", "user-read-private", "user-read-birthdate", "user-read-email", "user-top-read").toString(),
						expires_in = response.expiresIn.toLong()))
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
	
	fun loadAuthToken(id: String = "", accessToken: Token = authToken.value ?: Token(), code: String = ""): TokenLiveData {
        log("loadingToken...")
	    val tokenResourceObj = spotifyRepository.obtainTokenResource(id, accessToken, code) { authToken.removeSource(it) }
	    authToken.addSource(tokenResourceObj.obtainResource()) { tokenResource ->
            log(toString(tokenResource), "loadAuthTokenObserver")
            when (tokenResource?.status) {
	            Status.SUCCESS, Status.LOADING -> if (notNullEmpty(tokenResource.data?.access_token) && tokenResource.data != authToken.value) authToken.value = tokenResource.data else log("Resource: ${tokenResource.status} has null data", "loadAuthTokenObserver")
                Status.ERROR -> log("Error loading resource: ${tokenResource.message}")
                else -> log("Resource is null")
            }
        }
	    log("tokenResource added as authToken source")
        return authToken
    }
	
	fun loadMe(userId: String = "", accessToken: Token = authToken.value ?: Token()):
			SpotifyUserLiveData {
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
	
	val authToken: TokenLiveData
		get() = TokenLiveData.tInstance
	
	val spotifyUser: SpotifyUserLiveData
		get() = SpotifyUserLiveData.uInstance
	
}
