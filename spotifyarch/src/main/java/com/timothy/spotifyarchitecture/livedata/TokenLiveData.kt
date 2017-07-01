package com.timothy.spotifyarchitecture.livedata

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.content.Context
import android.support.annotation.MainThread

import com.timothy.spotifyarchitecture.entities.Token
import com.timothy.spotifyarchitecture.remote.NetworkBoundResource
import com.timothy.spotifyarchitecture.remote.Resource

import retrofit2.Call

/**
 * Created by tim on 6/4/17.
 */

class TokenLiveData : ResourceLiveData<Token>() {

    fun needsRefresh(): Boolean {
        return data()?.refresh_token?.isNotEmpty()!!
                && System.currentTimeMillis() >= data()?.expires_in!!
    }

}
