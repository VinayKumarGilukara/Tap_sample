package com.example.tap_sample.database

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object LocalDatabaseModule {

    @Provides
    @Singleton
    fun provideTapLocalDB(@ApplicationContext context: Context): TransDB {
        return Room.databaseBuilder(
            context.applicationContext,
            TransDB::class.java,
            "transaction.db"

        )
            .createFromAsset("database/transaction.db")
            .allowMainThreadQueries()
            .build()
    }


}