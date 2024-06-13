package com.example.tap_sample.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "hotsheet")
data class HotSheet(
    @PrimaryKey
    @ColumnInfo(name = "_id") val id:Int,
    @ColumnInfo(name ="comment") val comment:String?,
    @ColumnInfo(name ="dob") val dob:String?,
    @ColumnInfo(name ="exclusion_period") val exclusionPeriod:String?,
    @ColumnInfo(name ="id_or_dl") val dl:String?,
    @ColumnInfo(name ="lastname_or_firstname") val name:String?,
    @ColumnInfo(name ="no") val no:Int?,
    @ColumnInfo(name ="placed_on_list") val placedList:String?,
    @ColumnInfo(name ="service_category") val serviceCategory:String?,
    @ColumnInfo(name ="tap_card") val tapCard:String?,
     @ColumnInfo(name ="violation_code") val violationCode:String?,
    @ColumnInfo(name ="violation_description") val violationDescription:String?,
    @ColumnInfo(name ="vendorid") val vendorId: Int
)
