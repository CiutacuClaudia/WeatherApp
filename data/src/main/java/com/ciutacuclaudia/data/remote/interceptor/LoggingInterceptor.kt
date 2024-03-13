package com.ciutacuclaudia.data.remote.interceptor

import okhttp3.logging.HttpLoggingInterceptor
import timber.log.Timber

class LoggingInterceptor : HttpLoggingInterceptor.Logger {
    override fun log(message: String) {
        Timber.tag(LOG_TAG).d(message)
    }

    companion object {
        const val LOG_TAG = "NetworkRequest"
    }
}