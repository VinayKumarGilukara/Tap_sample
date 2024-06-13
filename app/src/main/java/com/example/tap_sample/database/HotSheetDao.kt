package com.example.tap_sample.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query


@Dao
interface HotSheetDao {

    @Query("SELECT * FROM hotsheet")
    fun getAllTopSheets(): LiveData<List<HotSheet>>

}