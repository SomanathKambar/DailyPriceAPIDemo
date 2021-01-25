package com.somanath.example.dailypriceapidemo.dependency

import android.content.Context
import com.somanath.example.dailypriceapidemo.api.PriceDetailsAPI
import com.somanath.example.dailypriceapidemo.database.GetInitialDataRxRemoteMediator
import com.somanath.example.dailypriceapidemo.database.PriceDetailsDatabase
import com.somanath.example.dailypriceapidemo.util.Utils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object PriceDetailsModule {
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        val interceptor : HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }

        val client : OkHttpClient = OkHttpClient.Builder().apply {
            this.addInterceptor(interceptor)
            this.readTimeout(30, TimeUnit.SECONDS)
            this.writeTimeout(30, TimeUnit.SECONDS)
        }.build()

        return Retrofit.Builder()
            .baseUrl(Utils.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    fun providePriceDetailsAPI(retrofit: Retrofit)  = retrofit.create(PriceDetailsAPI::class.java)

    @Provides
    @Singleton
    fun provideDataBase(@ApplicationContext context: Context) =  PriceDetailsDatabase.getInstance(context)

    @Provides
    @Singleton
    fun provideRemoteMediator(api: PriceDetailsAPI,database: PriceDetailsDatabase): GetInitialDataRxRemoteMediator{
        return GetInitialDataRxRemoteMediator(api,database)
    }
}