package com.cd.utility.utils

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import java.io.IOException
import com.cd.utility.model.Result
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException

fun <T> safeApi(apiCall: suspend () -> T) = flow {
    try {
        emit(Result.loading())
        val result = apiCall.invoke()
        emit(Result.Success(result))
    } catch (throwable: Throwable) {
        when (throwable) {
            is IOException -> emit(Result.NetworkError())
            is HttpException -> {
                try {
                    val code = throwable.code()
                    val detail = throwable.response()?.errorBody()?.source().toString()
                    Log.d("ErrorLog", detail)

                    when (detail) {
                        "[size=0]" -> emit(
                            Result.Error(
                                code,
                                throwable.localizedMessage
                            )
                        )
                        else -> emit(
                            Result.Error(
                                code,
                                detail
                                    .substringAfter(":\"").replace("\"}", "")
                                    .substringBefore("]")
                                    .replace("[text=[\"", "")
                                    .replace("\"", "")
                            )
                        )
                    }
                } catch (e: Exception) {
                    emit(Result.Error(throwable.code(), null))
                }
            }
            else -> {
                emit(Result.Error(null, throwable.localizedMessage))
            }
        }
    }

}.flowOn(Dispatchers.IO)