package com.example.tap_sample.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigInteger

@Entity(tableName = "lineroute")
data class LineRoute(
    @PrimaryKey
    @ColumnInfo(name = "_id") val id:Int,
    @ColumnInfo(name ="description") val description:String?,
    @ColumnInfo(name ="line_route_id") val lineRouteId:Int?,
    @ColumnInfo(name ="operator_id") val operatorId:Int?,
    @ColumnInfo(name ="route_number") val routeNumber:Int?,
    @ColumnInfo(name ="stop_point_id") val stopPointId:Int?,
    @ColumnInfo(name ="vendorid") val vendorId:Int,
    @ColumnInfo(name ="rail_status") val railStatus:Int?,
    @ColumnInfo(name ="transit_type") val transitType:Int?
)


