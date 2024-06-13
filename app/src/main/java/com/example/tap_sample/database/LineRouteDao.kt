package com.example.tap_sample.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query


@Dao
interface LineRouteDao {

    @Query("SELECT * FROM lineroute")
    fun searchDatabase(): LiveData<List<LineRoute>>

    @Query("SELECT * FROM lineroute WHERE description = :description")
     fun getLineRouteByDescription(description: String): LineRoute?


}