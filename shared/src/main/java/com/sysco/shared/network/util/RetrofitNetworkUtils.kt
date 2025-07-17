package com.sysco.shared.network.util


import com.sysco.shared.core.domain.model.Result
import com.sysco.shared.core.domain.model.error.DataError
import kotlinx.coroutines.ensureActive
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.nio.channels.UnresolvedAddressException
import kotlin.coroutines.coroutineContext

suspend fun <T> safeCall(apiCall: suspend () -> T): Result<T, DataError.NetworkError> {
    return try {
        Result.Success(apiCall())
    } catch (e: SocketTimeoutException) {
        Result.Error(DataError.NetworkError.REQUEST_TIMEOUT)
    } catch (e: UnknownHostException) {
        Result.Error(DataError.NetworkError.NO_INTERNET)
    } catch (e: UnresolvedAddressException) {
        Result.Error(DataError.NetworkError.NO_INTERNET)
    } catch (e: HttpException) {
        when (e.code()) {
            401 -> Result.Error(DataError.NetworkError.UNAUTHORIZED)
            403 -> Result.Error(DataError.NetworkError.FORBIDDEN)
            404 -> Result.Error(DataError.NetworkError.NOT_FOUND)
            in 500..599 -> Result.Error(DataError.NetworkError.SERVER_ERROR)
            else -> Result.Error(DataError.NetworkError.UNKNOWN)
        }
    } catch (e: IOException) {
        Result.Error(DataError.NetworkError.CONNECTION_ERROR)
    } catch (e: Exception) {
        coroutineContext.ensureActive()
        Result.Error(DataError.NetworkError.UNKNOWN)
    }
}