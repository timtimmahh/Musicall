package com.timothy.spotifyarchitecture.livedata

import android.arch.lifecycle.MediatorLiveData

import com.timothy.spotifyarchitecture.remote.Resource

/**
 * Created by tim on 6/8/17.
 */

abstract class ResourceLiveData<T> : MediatorLiveData<Resource<T>>() {

    init {
        value = Resource.loading<T>(null)
    }

    fun data(): T? = value!!.data

}
