package com.practice.demo.db

import com.practice.demo.domain.ApiService
import com.practice.demo.profileMatch.MatchProfileContract
import javax.inject.Inject

class MatchProfileRepository @Inject constructor(
    private val profileDao: ProfileDao
) {

    suspend fun getProfilesFromDb(): List<ProfileEntity> {
        return profileDao.getAllProfiles()
    }

    suspend fun insertProfiles(profiles: List<ProfileEntity>) {
        profileDao.insertProfiles(profiles)
    }

    suspend fun updateInteraction(uuid: String, status: MatchProfileContract.InteractionStatus) {
        profileDao.updateInteraction(uuid, status)
    }
}