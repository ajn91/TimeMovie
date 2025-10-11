package jafari.movie.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.ANDROID
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import jafari.movie.BuildConfig
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {

    private const val TIMEOUT_MS = 15_000L

    @Provides
    @Singleton
    fun providesJson(): Json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
        isLenient = true
    }

    @Provides
    @Singleton
    fun provideKtorClient(json: Json): HttpClient {
        return HttpClient(OkHttp) {
            // Logging
            install(Logging) {
                logger = Logger.ANDROID
                level = LogLevel.ALL
            }

            // JSON Serialization
            install(ContentNegotiation) {
                json(json)
            }

            // Timeout
            install(HttpTimeout) {
                requestTimeoutMillis = TIMEOUT_MS
                connectTimeoutMillis = TIMEOUT_MS
                socketTimeoutMillis = TIMEOUT_MS
            }

            // Default request configuration
            defaultRequest {
                url(BuildConfig.BASE_URL)
                header("Authorization", "Bearer ${BuildConfig.API_TOKEN}")
                contentType(ContentType.Application.Json)
            }

            // OkHttp specific configuration
            engine {
                // You can configure OkHttp specific features here
                // For example, add an interceptor for advanced logging or caching
            }
        }
    }
}
