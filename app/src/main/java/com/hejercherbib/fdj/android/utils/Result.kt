package com.hejercherbib.fdj.android.utils

sealed class Result<out T>(val data: T?, val message: String?) {

    data class Success<out R>(val _data: R?) : Result<R>(
        data = _data,
        message = null
    )

    data class Error(val exception: String?) : Result<Nothing>(
        data = null,
        message = exception
    )

    data class Loading(val isLoading: Boolean) : Result<Nothing>(
        data = null,
        message = null
    )
}
