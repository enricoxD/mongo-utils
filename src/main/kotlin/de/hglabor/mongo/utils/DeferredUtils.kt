package de.hglabor.mongo.utils

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
fun <T> Deferred<T>.then(onError: ((Throwable) -> Unit)? = null, callback: (T) -> Unit) {
    this.invokeOnCompletion {
        if (it is Throwable) {
            onError?.invoke(it)
            return@invokeOnCompletion
        }
        callback.invoke(this.getCompleted())
    }
}