package com.example.tap_sample.database


import androidx.room.Database
import androidx.room.RoomDatabase
@Database(entities = [Notifications::class], version = 1, exportSchema = false)
abstract class TransDB:RoomDatabase(){
}