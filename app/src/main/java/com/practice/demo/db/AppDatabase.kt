package com.practice.demo.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [ProfileEntity::class], version = 1, exportSchema = false)
@TypeConverters(InteractionStatusConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun profileDao(): ProfileDao
}