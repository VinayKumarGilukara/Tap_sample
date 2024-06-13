package com.example.tap_sample.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dagger.Provides
import kotlinx.coroutines.flow.Flow

@Dao
interface InspectorDao  {

    @Query("SELECT * FROM inspector WHERE username COLLATE NOCASE = :username AND password COLLATE NOCASE = :password")
    suspend fun getInspectorByUserNamePassword(username : String, password : String): Inspector?

}