package com.example.tap_sample.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query


@Dao
interface ServiceTypeDao {
   /* @Query("SELECT * FROM servicetype WHERE transit_type = :transitType")
    fun getServiceTypesByTransitType(transitType: Int): LiveData<List<ServiceType>>*/

       @Query("SELECT * FROM servicetype WHERE transit_type = :selectedTransitType")
    suspend fun getServiceTypeList(selectedTransitType: Int): List<ServiceType>


}