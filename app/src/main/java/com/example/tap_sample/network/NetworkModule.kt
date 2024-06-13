package com.example.tap_sample.network

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.tap_sample.database.RoomDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Singleton
    @Provides
    fun provideHttpClient(): OkHttpClient {
        return OkHttpClient
            .Builder()
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .build()
    }


    @Singleton
    @Provides
    fun provideConverterFactory(): GsonConverterFactory =
        GsonConverterFactory.create()


    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.TEST_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }


    @Singleton
    @Provides
    fun provideCurrencyService(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)

   @Singleton
    @Provides
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }

   /* @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): RoomDB {
        return RoomDB.getInstance(context)
    }*/

    @Singleton
    @Provides
    fun provideRoomDB(@ApplicationContext context: Context):RoomDB
    {
        return Room.databaseBuilder(context.applicationContext,RoomDB::class.java,"metrotap.db")
            .createFromAsset("database/metrotap.db")
            .build()
    }

    @Singleton
    @Provides
    fun provideInspectorDao(roomDB: RoomDB) = roomDB.inspectorDao()

    @Singleton
    @Provides
    fun provideLineRouteDao(roomDB: RoomDB)= roomDB.lineRouteDao()

    @Singleton
    @Provides
    fun provideHotSheetDao(roomDB: RoomDB)= roomDB.hotSheetDao()

    @Singleton
    @Provides
    fun provideServiceTypeDao(roomDB: RoomDB)= roomDB.serviceTypeDao()

    @Singleton
    @Provides
    fun provideNotification(roomDB: RoomDB) = roomDB.notificationDao()

}









