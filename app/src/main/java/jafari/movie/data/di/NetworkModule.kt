package jafari.movie.data.di


import AuthInterceptorOkHttpClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.filmtime.data.network.adapter.NetworkCallAdapterFactory
import jafari.movie.BuildConfig
import jafari.movie.data.network.TMDBService
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.CallAdapter
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {

//  private const val INTERCEPTOR_LOGGING_NAME = "INTERCEPTOR_LOGGING"
//  private const val INTERCEPTOR_HEADER_NAME = "INTERCEPTOR_HEADER"

  @Provides
  @Singleton
  fun providesJson(): Json {
    return Json {
      ignoreUnknownKeys = true
    }
  }

  //  @Provides
//  @Named(INTERCEPTOR_LOGGING_NAME)
//  fun provideHttpLoggingInterceptor(): Interceptor =
//    if (BuildConfig.DEBUG) {
//      HttpLoggingInterceptor().apply {
//        level = HttpLoggingInterceptor.Level.BODY
//      }
//    } else {
//      noOpInterceptor()
//    }
  @Provides
  @Singleton
  fun okHttpCallFactory(
    @AuthInterceptorOkHttpClient
    headerInterceptor: Interceptor,
  ): Call.Factory =
    OkHttpClient.Builder()
      .addInterceptor(
        HttpLoggingInterceptor()
          .apply {
            if (BuildConfig.DEBUG) {
              setLevel(HttpLoggingInterceptor.Level.BODY)
            }
          },
      )
      .addInterceptor(headerInterceptor)

      .build()

//  @Provides
//  @Singleton
//  fun provideOkhttpClient(
//    @Named(INTERCEPTOR_LOGGING_NAME) loggingInterceptor: Interceptor,
//    @Named(INTERCEPTOR_HEADER_NAME) headerInterceptor: Interceptor,
//  ): OkHttpClient {
//    return OkHttpClient
//      .Builder()
//      .connectTimeout(NETWORK_REQUEST_TIMEOUT, TimeUnit.SECONDS)
//      .readTimeout(NETWORK_REQUEST_READ_TIME, TimeUnit.SECONDS)
//      .addInterceptor(headerInterceptor)
//      .addInterceptor(loggingInterceptor)
//      .build()
//  }

  @Singleton
  @Provides
  fun provideRetrofit(
    okhttpCallFactory: dagger.Lazy<Call.Factory>,
    json: Json,
  ): Retrofit {
    return Retrofit.Builder()
      .baseUrl(BuildConfig.BASE_URL)
      // We use callFactory lambda here with dagger.Lazy<Call.Factory>
      // to prevent initializing OkHttp on the main thread.
      .callFactory { okhttpCallFactory.get().newCall(it) }
      .addConverterFactory(json.asConverterFactory("application/json".toMediaType())).build()
  }

//  @Provides
//  @Singleton
//  fun providesNetworkCallAdapterFactory(): CallAdapter.Factory {
//    return NetworkCallAdapterFactory()
//  }

  @Singleton
  @Provides
  fun provideTMDBService(retrofit: Retrofit): TMDBService {
    return retrofit.create(TMDBService::class.java)
  }

//  private fun noOpInterceptor(): Interceptor =
//    Interceptor { chain ->
//      chain.proceed(chain.request())
//    }

  @Provides
  @Singleton
  @AuthInterceptorOkHttpClient
  fun headerInterceptor(): Interceptor =
    Interceptor { chain ->
      val newRequest =
        chain.request().newBuilder()
          .addHeader("Authorization", "Bearer ${BuildConfig.API_TOKEN}")
          .build()
      chain.proceed(newRequest)
    }
}

