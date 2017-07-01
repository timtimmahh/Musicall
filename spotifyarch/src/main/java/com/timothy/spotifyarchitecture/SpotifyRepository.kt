package com.timothy.spotifyarchitecture

import android.arch.lifecycle.LiveData

import com.timothy.spotifyarchitecture.entities.SpotifyUser
import com.timothy.spotifyarchitecture.entities.Token
import com.timothy.spotifyarchitecture.livedata.SpotifyUserLiveData
import com.timothy.spotifyarchitecture.livedata.TokenLiveData
import com.timothy.spotifyarchitecture.remote.NetworkBoundResource
import com.timothy.spotifyarchitecture.retrofit.SpotifyCreator
import com.timothy.spotifyarchitecture.retrofit.SpotifyService
import com.timothy.spotifyarchitecture.retrofit.SpotifyServiceAuth
import com.timothy.spotifyarchitecture.retrofit.models.UserPrivate
import com.timothy.spotifyarchitecture.room.SpotifyDao

import java.util.HashMap

import javax.inject.Inject
import javax.inject.Singleton

import retrofit2.Call

/**
 * Created by tim on 5/30/17.
 */
@Singleton
class SpotifyRepository
@Inject
constructor(var serviceDao: SpotifyDao, var spotifyService: SpotifyService, var spotifyServiceAuth: SpotifyServiceAuth, var apiAuthenticator: SpotifyCreator.ApiAuthenticator) {

    fun getOAuthToken(id: String = "", accessToken: String = "", code: String = ""): TokenLiveData {
        if (apiAuthenticator.accessToken.isNotEmpty())
            apiAuthenticator.accessToken = accessToken
        val userLiveData = SpotifyUserLiveData.get()
        return object : NetworkBoundResource<TokenLiveData, Token, Token>(userLiveData.userToken) {
            override fun saveCallResult(item: Token) {
                val token = item.copy(expires_in = System.currentTimeMillis() + item.expires_in * 1000, userId = userLiveData.data()?.id ?: id)
                apiAuthenticator.accessToken = token.access_token
                if (asLiveData.needsRefresh())
                    serviceDao.updateToken(token)
                else if (!item.userId.isEmpty())
                    serviceDao.saveToken(token)
            }

            override fun loadFromDb(): LiveData<Token> {
                val tokenLiveData: LiveData<Token>
                if (!id.isEmpty())
                    tokenLiveData = serviceDao.loadAuthToken(id)
                else
                    tokenLiveData = serviceDao.loadAuthTokenByToken(apiAuthenticator.accessToken)
                if (tokenLiveData.value != null && !tokenLiveData.value!!.access_token.isEmpty())
                    apiAuthenticator.accessToken = tokenLiveData.value!!.access_token
                return tokenLiveData
            }

            override fun createCall(): Call<Token> {
                val body = HashMap<String, Any>()
                if (asLiveData.needsRefresh()) {
                    body.put("grant_type", "refresh_token")
                    body.put("refresh_token", asLiveData.data()!!.refresh_token)
                } else {
                    body.put("grant_type", "authorization_code")
                    body.put("code", code)
                    body.put("redirect_uri", Auth.REDIRECT_URI)
                }
                body.put("client_id", Auth.SPOTIFY_CLIENT_ID)
                body.put("client_secret", Auth.SPOTIFY_CLIENT_SECRET)
                return spotifyServiceAuth.getToken(body)
            }

            override fun shouldFetch(data: Token?): Boolean {
                return data == null || code.isNotEmpty() || asLiveData.needsRefresh()
            }
        }.asLiveData
    }

    fun saveToken(tokenLiveData: TokenLiveData) {
        if (tokenLiveData.data() != null)
            serviceDao.saveToken(tokenLiveData.data()!!)
    }

    fun getMe(userId: String = "", accessToken: String = ""): SpotifyUserLiveData {
        return object : NetworkBoundResource<SpotifyUserLiveData, SpotifyUser, UserPrivate>(SpotifyUserLiveData.get()) {

            override fun saveCallResult(item: UserPrivate) {
                serviceDao.saveMe(SpotifyUser(asLiveData.userToken.data()?.access_token!!, item))
            }

            override fun loadFromDb(): LiveData<SpotifyUser> {
                val userLiveData: LiveData<SpotifyUser>
                val id: String = if (userId.isNotEmpty()) userId else if (asLiveData.userToken.data()?.userId?.isNotEmpty()!!) asLiveData.userToken.data()!!.userId else ""
                if (id.isNotEmpty())
                    userLiveData = serviceDao.loadMe(id)
                else
                    userLiveData = serviceDao.loadMeByToken(accessToken)
                return userLiveData
            }

            override fun createCall(): Call<UserPrivate> {
                return spotifyService.me
            }

        }.asLiveData
    }

    fun updateMe(spotifyUserLiveData: SpotifyUserLiveData) {
        if (spotifyUserLiveData.data() != null)
            serviceDao.updateMe(spotifyUserLiveData.data()!!)
    }

}
