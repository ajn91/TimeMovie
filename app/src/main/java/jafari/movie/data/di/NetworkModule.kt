package jafari.movie.data.di

import AuthInterceptorOkHttpClient
import android.util.Log
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import jafari.movie.BuildConfig
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {

    @Provides
    @Singleton
    fun providesJson(): Json {
        return Json {
            ignoreUnknownKeys = true
            coerceInputValues = true
        }
    }

    @Provides
    @Singleton
    fun provideKtorClient(json: Json): HttpClient {
        return HttpClient(CIO) {
            // Logging
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        Log.v("KtorLogger", message)
                    }
                }
                level = LogLevel.ALL
            }

            // JSON Serialization
            install(ContentNegotiation) {
                json(json)
            }

            // Timeout
            install(HttpTimeout) {
                requestTimeoutMillis = 15_000L
                connectTimeoutMillis = 15_000L
                socketTimeoutMillis = 15_000L
            }

            // Default request configuration
            defaultRequest {
                url(BuildConfig.BASE_URL)
                header("Authorization", "Bearer ${BuildConfig.API_TOKEN}")
                contentType(ContentType.Application.Json)
            }
        }
    }

    // --- Keeping Retrofit setup for incremental migration (commented out) ---

//    @Singleton
//    @Provides
//    fun provideRetrofit(
//        okhttpCallFactory: dagger.Lazy<Call.Factory>,
//        json: Json,
//    ): Retrofit {
//        return Retrofit.Builder()
//            .baseUrl(BuildConfig.BASE_URL)
//            .callFactory { okhttpCallFactory.get().newCall(it) }
//            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
//            .build()
//    }

//    @Singleton
//    @Provides
//    fun provideTMDBService(retrofit: Retrofit): TMDBService {
//        return retrofit.create(TMDBService::class.java)
//    }
    
//    @Provides
//    @Singleton
//    @AuthInterceptorOkHttpClient
//    fun headerInterceptor(): Interceptor = 
//        Interceptor { chain ->
//            val newRequest =
//                chain.request().newBuilder()
//                    .addHeader("Authorization", "Bearer ${BuildConfig.API_TOKEN}")
//                    .build()
//            chain.proceed(newRequest)
//        }
}
