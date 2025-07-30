package com.practice.demo.db

import androidx.room.*
import com.practice.demo.profileMatch.MatchProfileContract

@Dao
interface ProfileDao {

    @Query("SELECT * FROM profiles")
    suspend fun getAllProfiles(): List<ProfileEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProfiles(profiles: List<ProfileEntity>)

    @Update
    suspend fun updateProfile(profile: ProfileEntity)

    @Query("UPDATE profiles SET interactionStatus = :status WHERE uuid = :uuid")
    suspend fun updateInteraction(uuid: String, status: MatchProfileContract.InteractionStatus)
}
