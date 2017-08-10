package com.timmahh.spotifyweb.retrofit

import com.timmahh.spotifyweb.notNullEmpty
import retrofit2.Response

class SpotifyError : Exception {
	
	constructor(detailMessage: String) : super(detailMessage)
	
	constructor(cause: Throwable) : super(cause)

    companion object {

        fun <T> fromResponse(response: Response<T>): SpotifyError {
	
	        val error: String? = response.errorBody()?.string()
	
	        if (notNullEmpty(error)) {
		        return SpotifyError(error !!)
            } else {
                return SpotifyError(Throwable("Can't read error response"))
            }
        }
    }
}
