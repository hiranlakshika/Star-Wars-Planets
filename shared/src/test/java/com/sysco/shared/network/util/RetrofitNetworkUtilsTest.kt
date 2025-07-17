package com.sysco.shared.network.util

import com.sysco.shared.core.domain.model.Result
import com.sysco.shared.core.domain.model.error.DataError
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.nio.channels.UnresolvedAddressException

@ExperimentalCoroutinesApi
class RetrofitNetworkUtilsTest {

    @Test
    fun `safeCall returns Success when API call succeeds`() = runTest {

        val expectedResult = "Success"
        val apiCall: suspend () -> String = { expectedResult }

        val result = safeCall(apiCall)

        assertTrue(result is Result.Success)
        assertEquals(expectedResult, (result as Result.Success).data)
    }

    @Test
    fun `safeCall returns REQUEST_TIMEOUT error when SocketTimeoutException is thrown`() = runTest {

        val apiCall: suspend () -> String = { throw SocketTimeoutException() }

        val result = safeCall(apiCall)

        assertTrue(result is Result.Error)
        assertEquals(DataError.NetworkError.REQUEST_TIMEOUT, (result as Result.Error).error)
    }

    @Test
    fun `safeCall returns NO_INTERNET error when UnknownHostException is thrown`() = runTest {

        val apiCall: suspend () -> String = { throw UnknownHostException() }

        val result = safeCall(apiCall)

        assertTrue(result is Result.Error)
        assertEquals(DataError.NetworkError.NO_INTERNET, (result as Result.Error).error)
    }

    @Test
    fun `safeCall returns NO_INTERNET error when UnresolvedAddressException is thrown`() = runTest {

        val apiCall: suspend () -> String = { throw UnresolvedAddressException() }

        val result = safeCall(apiCall)

        assertTrue(result is Result.Error)
        assertEquals(DataError.NetworkError.NO_INTERNET, (result as Result.Error).error)
    }

    @Test
    fun `safeCall returns UNAUTHORIZED error when HttpException with 401 status code is thrown`() =
        runTest {

            val mockResponse =
                Response.error<String>(401, "".toByteArray().toResponseBody(null))
            val httpException = HttpException(mockResponse)
            val apiCall: suspend () -> String = { throw httpException }

            val result = safeCall(apiCall)

            assertTrue(result is Result.Error)
            assertEquals(DataError.NetworkError.UNAUTHORIZED, (result as Result.Error).error)
        }

    @Test
    fun `safeCall returns FORBIDDEN error when HttpException with 403 status code is thrown`() =
        runTest {

            val mockResponse =
                Response.error<String>(403, "".toByteArray().toResponseBody(null))
            val httpException = HttpException(mockResponse)
            val apiCall: suspend () -> String = { throw httpException }

            val result = safeCall(apiCall)

            assertTrue(result is Result.Error)
            assertEquals(DataError.NetworkError.FORBIDDEN, (result as Result.Error).error)
        }

    @Test
    fun `safeCall returns NOT_FOUND error when HttpException with 404 status code is thrown`() =
        runTest {

            val mockResponse =
                Response.error<String>(404, "".toByteArray().toResponseBody(null))
            val httpException = HttpException(mockResponse)
            val apiCall: suspend () -> String = { throw httpException }

            val result = safeCall(apiCall)

            assertTrue(result is Result.Error)
            assertEquals(DataError.NetworkError.NOT_FOUND, (result as Result.Error).error)
        }

    @Test
    fun `safeCall returns SERVER_ERROR error when HttpException with 500 status code is thrown`() =
        runTest {

            val mockResponse =
                Response.error<String>(500, "".toByteArray().toResponseBody(null))
            val httpException = HttpException(mockResponse)
            val apiCall: suspend () -> String = { throw httpException }

            val result = safeCall(apiCall)

            assertTrue(result is Result.Error)
            assertEquals(DataError.NetworkError.SERVER_ERROR, (result as Result.Error).error)
        }

    @Test
    fun `safeCall returns UNKNOWN error when HttpException with unexpected status code is thrown`() =
        runTest {

            val mockResponse =
                Response.error<String>(418, "".toByteArray().toResponseBody(null))
            val httpException = HttpException(mockResponse)
            val apiCall: suspend () -> String = { throw httpException }

            val result = safeCall(apiCall)

            assertTrue(result is Result.Error)
            assertEquals(DataError.NetworkError.UNKNOWN, (result as Result.Error).error)
        }

    @Test
    fun `safeCall returns CONNECTION_ERROR error when IOException is thrown`() = runTest {

        val apiCall: suspend () -> String = { throw IOException() }

        val result = safeCall(apiCall)

        assertTrue(result is Result.Error)
        assertEquals(DataError.NetworkError.CONNECTION_ERROR, (result as Result.Error).error)
    }

    @Test
    fun `safeCall returns UNKNOWN error when unexpected Exception is thrown`() = runTest {

        val apiCall: suspend () -> String = { throw IllegalStateException() }

        val result = safeCall(apiCall)

        assertTrue(result is Result.Error)
        assertEquals(DataError.NetworkError.UNKNOWN, (result as Result.Error).error)
    }
}