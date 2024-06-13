package com.example.tap_sample.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "servicetype")
data class ServiceType(
    @PrimaryKey
    @ColumnInfo(name = "_id") val id:Int,
    @ColumnInfo(name = "transit_type") val transitType:Int?,
    @ColumnInfo(name = "fare_level") val fareLevel:Int?,
    @ColumnInfo(name = "fare_mode") val fareMode:Int?,
    @ColumnInfo(name = "time_category") val timeCategory:Int?,
    @ColumnInfo(name = "service_type_id") val serviceTypeId:Int?,
    @ColumnInfo(name = "vendorid") val vendorId:Int?
)
