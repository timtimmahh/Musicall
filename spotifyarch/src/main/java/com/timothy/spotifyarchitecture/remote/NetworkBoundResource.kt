/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.timothy.spotifyarchitecture.remote

import android.arch.lifecycle.LiveData
import android.os.AsyncTask
import android.support.annotation.MainThread
import android.support.annotation.WorkerThread
import android.util.Log

import com.timothy.spotifyarchitecture.livedata.ResourceLiveData
import com.timothy.spotifyarchitecture.retrofit.SpotifyCallback
import com.timothy.spotifyarchitecture.retrofit.SpotifyError

import retrofit2.Call
import retrofit2.Response

abstract class NetworkBoundResource<out ResultLiveData : ResourceLiveData<ResultType>, ResultType, RequestType> @MainThread
protected constructor(val asLiveData: ResultLiveData) {

    init {
        asLiveData.value = Resource.loading<ResultType>(null)
        @Suppress("LeakingThis")
        val dbSource = loadFromDb()
        asLiveData.addSource(dbSource) { data ->
            asLiveData.removeSource(dbSource)
            if (shouldFetch(data)) {
                fetchFromNetwork(dbSource)
            } else {
                asLiveData.addSource(dbSource) { newData ->
                    asLiveData.setValue(Resource.success(newData!!))
                }
            }
        }
    }

    private fun fetchFromNetwork(dbSource: LiveData<ResultType>) {
        asLiveData.addSource(dbSource) { newData -> asLiveData.setValue(Resource.loading(newData)) }
        createCall().enqueue(object : SpotifyCallback<RequestType>() {
            override fun onResponse(call: Call<RequestType>, response: Response<RequestType>, payload: RequestType) {
                asLiveData.removeSource(dbSource)
                saveResultAndReInit(payload)
            }

            override fun onFailure(call: Call<RequestType>, error: SpotifyError) {
                onFetchFailed()
                asLiveData.removeSource(dbSource)
                asLiveData.addSource(dbSource) { newData -> asLiveData.setValue(Resource.error<ResultType>(error.message!!, newData)) }
            }
        })
    }

    @MainThread
    private fun saveResultAndReInit(response: RequestType) {
        SaveAsync(this, response).execute()
    }

    @WorkerThread
    protected abstract fun saveCallResult(item: RequestType)

    @MainThread
    protected open fun shouldFetch(data: ResultType?): Boolean {
        return data == null
    }

    @MainThread
    protected abstract fun loadFromDb(): LiveData<ResultType>

    @MainThread
    protected abstract fun createCall(): Call<RequestType>

    @MainThread
    private fun onFetchFailed() {
        Log.d(javaClass.simpleName, "Failed to fetch " + asLiveData.javaClass.getSimpleName())
    }

    private class SaveAsync<ResultLiveData : ResourceLiveData<ResultType>, ResultType, RequestType> internal constructor(internal var resource: NetworkBoundResource<ResultLiveData, ResultType, RequestType>, internal var response: RequestType) : AsyncTask<Void, Void, Void>() {

        override fun doInBackground(vararg voids: Void): Void? {
            resource.saveCallResult(response)
            return null
        }

        override fun onPostExecute(aVoid: Void) {
            resource.asLiveData.addSource(resource.loadFromDb()) { newData ->
                resource.asLiveData.setValue(Resource.success(newData!!))
            }
        }
    }

}
