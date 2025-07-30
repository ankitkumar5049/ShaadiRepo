package com.practice.demo.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.practice.demo.profileMatch.MatchProfileContract

@Entity(tableName = "profiles")
data class ProfileEntity(
    @PrimaryKey val uuid: String,
    val name: String?,
    val age: Int?,
    val city: String?,
    val country: String?,
    val phone: String?,
    val email: String?,
    val pictureUrl: String?,
    @TypeConverters(InteractionStatusConverter::class)
    val interactionStatus: MatchProfileContract.InteractionStatus = MatchProfileContract.InteractionStatus.NONE
)
