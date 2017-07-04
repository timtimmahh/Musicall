package com.timothy.spotifyarchitecture.livedata

import android.arch.lifecycle.MediatorLiveData
import com.timothy.spotifyarchitecture.entities.Token
import com.timothy.spotifyarchitecture.notNullEmpty

/**
 * LiveData implementation to create a MediatorLiveData<Token>
 */

class TokenLiveData private constructor() : MediatorLiveData<Token>() {

    companion object {
        @JvmField
        var tInstance: TokenLiveData = TokenLiveData()

        @JvmStatic
        fun needsRefresh(): Boolean {
            return notNullEmpty(tInstance.value?.refresh_token)
                    && System.currentTimeMillis() >= tInstance.value!!.expires_in
        }
    }

}
