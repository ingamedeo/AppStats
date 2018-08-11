package com.ingamedeo.appstats

import kotlinx.coroutines.experimental.CancellableContinuation
import kotlinx.coroutines.experimental.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * @throws IllegalStateException
 */
suspend fun <T> Call<T>.await(): T = suspendCancellableCoroutine { continuation ->

    val callback = object : Callback<T> {
        override fun onFailure(call: Call<T>, t: Throwable) = continuation.tryToResume { throw t }
        override fun onResponse(call: Call<T>, response: Response<T>) = continuation.tryToResume {
            response.isSuccessful || throw IllegalStateException("Http error ${response.code()}")
            response.body() ?: throw IllegalStateException("Response body is null")
        }
    }

    enqueue(callback)
}

private inline fun <T> CancellableContinuation<T>.tryToResume(getter: () -> T) {
    isActive || return
    try { resume(getter()) }
    catch (exception: Throwable) { resumeWithException(exception) }
}
