package com.timmahh.spotifyweb.retrofit.models

/**
 * [Error object model](https://developer.spotify.com/web-api/object-model/#error-object)
 */
class ErrorDetails {
    var status: Int = 0
    var message: String = ""
}
