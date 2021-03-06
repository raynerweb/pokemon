package br.com.raynerweb.pokemon.di

import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

const val BASE_URL = "https://vortigo.blob.core.windows.net/files/pokemon/data/"

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(
        converter: Converter.Factory,
        okHttpClient: OkHttpClient,
    ): Retrofit {
        val builder = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(converter)
            .client(okHttpClient)
        return builder.build()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder().apply {
            hostnameVerifier { _, _ -> true }

            addNetworkInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })

            connectTimeout(30, TimeUnit.SECONDS)
            readTimeout(60, TimeUnit.SECONDS)
        }.build()

    @Provides
    @Singleton
    fun proviceConverterFactory(): Converter.Factory {
        val builder = GsonBuilder().apply {
            registerTypeAdapter(Date::class.java, JsonDeserializer { json, _, _ ->
                Date(json.asLong)
            })
        }.create()
        return GsonConverterFactory.create(builder)
    }

}