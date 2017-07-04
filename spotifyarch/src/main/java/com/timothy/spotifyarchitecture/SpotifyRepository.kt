package com.timothy.spotifyarchitecture

import android.annotation.SuppressLint
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.MutableLiveData
import android.os.AsyncTask
import com.timothy.spotifyarchitecture.entities.SpotifyUser
import com.timothy.spotifyarchitecture.entities.Token
import com.timothy.spotifyarchitecture.livedata.SpotifyUserLiveData
import com.timothy.spotifyarchitecture.livedata.TokenLiveData
import com.timothy.spotifyarchitecture.remote.NetworkBoundResource
import com.timothy.spotifyarchitecture.remote.Resource
import com.timothy.spotifyarchitecture.remote.Status
import com.timothy.spotifyarchitecture.retrofit.*
import com.timothy.spotifyarchitecture.retrofit.models.UserPrivate
import com.timothy.spotifyarchitecture.room.SpotifyDao
import retrofit2.Call
import retrofit2.Response
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Manages Spotify database data as well as network
 */
@Singleton
class SpotifyRepository
@Inject
constructor(var serviceDao: SpotifyDao, var spotifyService: SpotifyService, var spotifyServiceAuth: SpotifyServiceAuth, var apiAuthenticator: SpotifyCreator.ApiAuthenticator) {

    fun getToken(id: String = "", accessToken: String = "", code: String = ""): MediatorLiveData<Resource<Token>> {
        log("getting token...")
        val tokenResource: MediatorLiveData<Resource<Token>> = MediatorLiveData()
        tokenResource.value = Resource.loading(null)
        log("obtaining token from db...")
        val obj = loadToken(id, accessToken)
        log("loaded token from db...${com.timothy.spotifyarchitecture.toString(obj)}")
        tokenResource.addSource(obj) { token: Token? ->
            log(com.timothy.spotifyarchitecture.toString(token))
            if (shouldFetchToken(token)) {
                log("fetching new token...")
                val newToken = fetchToken(code)
                log("observing token for updates")
                tokenResource.addSource(newToken) {
                    log(toString(it))
                    when (it?.status) {
                        Status.SUCCESS -> if (notNull(it.data)) {
                            log("successful token")
                            @SuppressLint("StaticFieldLeak")
                            object : AsyncTask<Void, Void, Void>() {
                                override fun doInBackground(vararg p0: Void?): Void? {
                                    tokenResource.removeSource(newToken)
                                    log("saving token...")
                                    saveToken(it.data!!)
                                    return null
                                }

                                override fun onPostExecute(result: Void?) {
                                    log("reloading token from db...")
                                    tokenResource.addSource(loadToken()) {
                                        log("token loaded")
                                        tokenResource.value = Resource.success(it!!)
                                    }
                                }
                            }
                        }
                        Status.ERROR, Status.LOADING -> {
                            tokenResource.value = it; log("token ${it.status}")
                        }
                        else -> {
                            log("null status")
                        }
                    }
                }
            } else if (notNull(token)) {
                tokenResource.value = Resource.success(token!!); log("keeping token from db")
            } else log("unknown error obtaining token")
        }
        log("returning livedata for observing")
        return tokenResource
    }

    fun loadToken(id: String = "", accessToken: String = ""): LiveData<Token> {
        val token: LiveData<Token> =
                if (notEmpty(id))
                    serviceDao.loadAuthToken(id)
                else if (notEmpty(accessToken))
                    serviceDao.loadAuthTokenByToken(accessToken)
                else MutableLiveData<Token>()
        if (notNullEmpty(token.value?.access_token))
            apiAuthenticator.accessToken = token.value!!.access_token
        log("loadFromDb:" + toString(token))
        return token
    }

    fun shouldFetchToken(data: Token?): Boolean {
        return isNull(data) || TokenLiveData.needsRefresh()
    }

    fun fetchToken(code: String = ""): LiveData<Resource<Token>> {
        val tokenResource: MutableLiveData<Resource<Token>> = MutableLiveData()
        tokenResource.value = Resource.loading(null)
        val body = HashMap<String, Any>()
        if (TokenLiveData.needsRefresh() && empty(code)) {
            body.put("grant_type", "refresh_token")
            body.put("refresh_token", TokenLiveData.tInstance.value?.refresh_token!!)
        } else if (notEmpty(code)) {
            body.put("grant_type", "authorization_code")
            body.put("code", code)
            body.put("redirect_uri", Auth.REDIRECT_URI)
        } else logE("Trying to fetch token with empty code")
        body.put("client_id", Auth.SPOTIFY_CLIENT_ID)
        body.put("client_secret", Auth.SPOTIFY_CLIENT_SECRET)
        log("createCall:" + toString(body))
        spotifyServiceAuth.getToken(body).enqueue(object : SpotifyCallback<Token>() {
            override fun onResponse(call: Call<Token>, response: Response<Token>, payload: Token) {
                payload.expires_in = System.currentTimeMillis() + payload.expires_in * 1000
                apiAuthenticator.accessToken = payload.access_token
                tokenResource.value = Resource.success(payload)
            }

            override fun onFailure(call: Call<Token>, error: SpotifyError) {
                tokenResource.value = Resource.error(error.message ?: "", null)
                logE("Failed to fetch " + toString(error))
            }

        })
        return tokenResource
    }

    fun saveToken(item: Token) {
        log("saveCallResult:" + toString(item))
        if (isNull(item)) return
        serviceDao.saveToken(item)
    }

    fun getMe(userId: String = "", accessToken: String = ""): MediatorLiveData<Resource<SpotifyUser>> {
        return object : NetworkBoundResource<SpotifyUser, UserPrivate>() {

            override fun saveCallResult(item: UserPrivate) {
                if (isNull(item)) return
                serviceDao.saveMe(SpotifyUser(getFirstNotNullEmpty(TokenLiveData.tInstance.value?.access_token, apiAuthenticator.accessToken, accessToken).toString(), item))
            }

            override fun loadFromDb(): LiveData<SpotifyUser> {
                val id: String = getFirstNotNullEmpty(userId, TokenLiveData.tInstance.value?.userId).toString()
                val userLiveData: LiveData<SpotifyUser> =
                        if (notEmpty(id))
                            serviceDao.loadMe(id)
                        else if (notEmpty(accessToken))
                            serviceDao.loadMeByToken(accessToken)
                        else MediatorLiveData<SpotifyUser>()
                userLiveData.log("loadFromDb:" + toString(userLiveData))
                return userLiveData
            }

            override fun createCall(): Call<UserPrivate> {
                return spotifyService.me
            }

            override fun shouldFetch(data: SpotifyUser?): Boolean {
                return super.shouldFetch(data)
            }

            override fun onFetchFailed(call: Call<UserPrivate>, error: Throwable) {
                log("Failed to fetch " + toString(error))
            }
        }.asLiveData
    }

    fun updateMe(spotifyUserLiveData: SpotifyUserLiveData) {
        if (isNull(spotifyUserLiveData.value))
            serviceDao.updateMe(spotifyUserLiveData.value!!)
    }

    fun updateApiAuthenticator(accessToken: String = "") {
        if (notEmpty(accessToken))
            apiAuthenticator.accessToken = accessToken
    }

}
