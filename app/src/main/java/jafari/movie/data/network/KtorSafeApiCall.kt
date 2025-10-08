package jafari.movie.data.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.request
import jafari.movie.data.network.adapter.NetworkResponse
import jafari.movie.domain.errors.toDataErrorType
import java.io.IOException

suspend inline fun <reified S : Any, reified E : Any> HttpClient.safeRequest(
    block: HttpRequestBuilder.() -> Unit,
): NetworkResponse<S, E> =
    try {
        val response = request { block() }
        NetworkResponse.Success(response.body())
    } catch (e: ClientRequestException) {
        NetworkResponse.ApiError(e.response.body(), e.response.status.value)
    } catch (e: ServerResponseException) {
        NetworkResponse.ApiError(e.response.body(), e.response.status.value)
    } catch (e: RedirectResponseException) {
        NetworkResponse.UnknownError(e)
    } catch (e: IOException) {
        NetworkResponse.NetworkError(e)
    } catch (e: Exception) {
        NetworkResponse.UnknownError(e)
    } catch (throwable: Throwable){
        throwable.toDataErrorType()
    }
)

