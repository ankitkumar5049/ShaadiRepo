package com.practice.demo.db

import androidx.room.TypeConverter
import com.practice.demo.profileMatch.MatchProfileContract

class InteractionStatusConverter {
    @TypeConverter
    fun fromStatus(status: MatchProfileContract.InteractionStatus): String = status.name

    @TypeConverter
    fun toStatus(status: String): MatchProfileContract.InteractionStatus = MatchProfileContract.InteractionStatus.valueOf(status)
}